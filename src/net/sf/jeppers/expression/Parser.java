/* 
 * Copyright (c) 2004, Cameron Zemek
 * 
 * This file is part of JExpression.
 * 
 * JExpression is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * JExpression is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sf.jeppers.expression;

import java.util.*;

/**
 * This class reads tokens from an expression using a <code>Lexer<code> 
 * and converts the expression to postfix form.
 * 
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class Parser {
	// Look ahead buffer for reading tokens from the lexer
	// This is used to reduce memory usage
	TokenBuffer lookAheadBuffer;
	
	// token stream in postfix order
	private List output;
	
	// stack to store operators for converting the 
	// expression to postfix form
	private Stack opStack = new Stack();
	
	private List references = new LinkedList();
	
	public Parser(Lexer lexer){
		lookAheadBuffer = new TokenBuffer(lexer, 2);
		output = new LinkedList(); // use LinkedList since add(Object) method is O(1)
	}
	
	/**
	 * Get the list of variables in the expression
	 * @return List of variable names
	 */
	public List getReferences() {
		if (output.isEmpty()) {
			expression();
		}
		return references;
	}
	
	/**
	 * Convert the expression into postfix form
	 * @return Array of expression tokens in postfix form
	 */
	public Token[] postfix() {
		if (output.isEmpty()) {
			expression();
		}
		return (Token []) output.toArray(new Token[output.size()]);
	}
	
	private void error(String message) {
		throw new ParseException(message);
	}
	
	private TokenType lookAhead(int i) {
		if (lookAheadBuffer.isEmpty() || i > lookAheadBuffer.size()) {
			return null; // EOF
		}		
		Token token = lookAheadBuffer.getToken(i-1); // 1-based index
		return token.getType();
	}
	
	private int noArgs; // counts the number of arguments in function call
	private Token method; // reference to method token
	private Token lastToken; // reference to last token
	
	private void updateStack(Token token) {
		TokenType tokenType = token.getType();
		if (tokenType == TokenType.METHOD_CALL) {
			noArgs = 0;
			method = token;
		}
		
		if (method != null && tokenType == TokenType.COMMA) {
			noArgs++;
		}
		
		if (method != null && tokenType == TokenType.RPAREN) {
			if (lastToken.getType() != TokenType.LPAREN) {
				noArgs++;
			}
			method.setNumberOfArguments(noArgs);
			method = null;
			lastToken = null;
		}
		
		if (method != null) {
			lastToken = token;
		}
		
		// Operands are written to output when encountered
		if (TokenType.isOperand(token)) {
			writeToken(token);
		}
		
		// If this is the last token, pop the
		// remaining operators from the stack
		if (lookAheadBuffer.isEmpty()) {
			while(! opStack.isEmpty()) {
				Token op = (Token) opStack.pop();
				writeToken(op);
			}
			return;
		}
		
		// If the token is an operand then we are done
		if (TokenType.isOperand(token)) {
			return;
		}

		if (tokenType == TokenType.RPAREN) {
			// pop until we match left paren
			Token op;
			do {
				op = (Token) opStack.pop();
				writeToken(op);
			} while (op.getType() != TokenType.LPAREN);
		}
		
		// If this is the opStack is empty  or if token is left paren
		// we push it onto the stack
		if (opStack.isEmpty() || tokenType == TokenType.LPAREN) {
			opStack.push(token);
			return;
		}
						
		// we pop operators off the stack that are of higher or
		// equal precendce then token
		Token op = (Token) opStack.peek();
		while (TokenType.precendenceLevel(op.getType()) 
				>= TokenType.precendenceLevel(tokenType)) {
			if (op.getType() == TokenType.LPAREN) {
				break;
			}
			Token temp = (Token) opStack.pop();
			writeToken(temp);
			if(opStack.isEmpty()) {
				break;
			}
			op = (Token) opStack.peek();
		}
		opStack.push(token);		
	}
	
	private Token match(TokenType tokenType) {
		Token token = lookAheadBuffer.readToken();
		if (token.getType() == tokenType) {
			updateStack(token);
			return token;
		} else {
			error("Expecting type " + tokenType + " but got "
					+ token.getType());
		}
		return null;
	}
	
	private void writeToken(Token token) {
		if (token.getType() == TokenType.LPAREN ||
			token.getType() == TokenType.RPAREN ||
			token.getType() == TokenType.COMMA) {
			return; // ignore parens
		}
		output.add(token);
	}	
	
	private void expression() {
		stringExpression();
	}
	
	private void sumExpression() {
		// term ((PLUS^|MINUS^) term)* 
		term();
		while (lookAhead(1) == TokenType.PLUS || lookAhead(1) == TokenType.MINUS) {
			if (lookAhead(1) == TokenType.PLUS) {
				match(TokenType.PLUS);
			} else if (lookAhead(1) == TokenType.MINUS) {
				match(TokenType.MINUS);								
			}
			term();
		}
	}
	
	private void term() {
		// factor ((MUL^|DIV^|MOD^) factor)*
		factor();
		while (lookAhead(1) == TokenType.MULTIPLY ||
				lookAhead(1) == TokenType.DIVIDE ||
				lookAhead(1) == TokenType.MOD) {
			if (lookAhead(1) == TokenType.MULTIPLY) {
				match(TokenType.MULTIPLY);
			} else if (lookAhead(1) == TokenType.DIVIDE) {
				match(TokenType.DIVIDE);
			} else if (lookAhead(1) == TokenType.MOD) {
				match(TokenType.MOD);
			}
			factor();
		}
	}
	
	private void factor() {
		// signExpr (POW^ signExpr)? 
		signExpression();
		if (lookAhead(1) == TokenType.POWER) {
			match(TokenType.POWER);
			signExpression();
		}
	}
	
	private void signExpression() {
		// (
        // m:MINUS^ {#m.setType(SIGN_MINUS); }
		// |	p:PLUS^  {#p.setType(SIGN_PLUS); }
		// )? value
		Token signToken = null;
		if (lookAhead(1) == TokenType.MINUS) {
			signToken = lookAheadBuffer.getToken(0);
			signToken.setType(TokenType.SIGN_MINUS);
			match(TokenType.SIGN_MINUS);			
		} else if (lookAhead(1) == TokenType.PLUS) {
			signToken = lookAheadBuffer.getToken(0);
			signToken.setType(TokenType.SIGN_PLUS);
			match(TokenType.SIGN_PLUS);			
		}
		value();
	}
	
	private void value() {
		// (ID LPAREN) => func
		// | atom
		if (lookAhead(1) == TokenType.VAR && lookAhead(2) == TokenType.LPAREN) {
			function();
		} else {
			atom();
		}
	}
	
	private void function() {
		// f:ID^ LPAREN! argList RPAREN!
		Token funcName = lookAheadBuffer.getToken(0);
		funcName.setType(TokenType.METHOD_CALL);
		match(TokenType.METHOD_CALL);		
		match(TokenType.LPAREN);
		if (lookAhead(1) != TokenType.RPAREN) {			
			argumentList();			
		}
		match(TokenType.RPAREN);
	}
	
	private void argumentList() {
		// (stringExpr (COMMA! stringExpr)* )?
		stringExpression();	
		while (lookAhead(1) == TokenType.COMMA) {
			match(TokenType.COMMA);			
			stringExpression();
		}		
	}
	
	private void atom() {
		// NUM
		// | LPAREN^ sumExpr RPAREN! 
		// | variable 
		if (lookAhead(1) == TokenType.NUM) {
			match(TokenType.NUM);
		} else if (lookAhead(1) == TokenType.LPAREN) {
			match(TokenType.LPAREN);
			stringExpression();
			match(TokenType.RPAREN);
		} else {
			variable();
		}
	}
	
	private void variable() {
		Token token = match(TokenType.VAR);
		references.add(token.getText());
	}
	
	private void booleanExpression() {
		// boolTerm (OR^ boolTerm)*
		booleanTerm();
		while (lookAhead(1) == TokenType.OR) {
			match(TokenType.OR);
			booleanTerm();
		}
	}
	
	private void booleanTerm() {
		// boolFactor (AND^ boolFactor)*
		booleanFactor();
		while (lookAhead(1) == TokenType.AND) {
			match(TokenType.AND);
			booleanFactor();
		}
	}
	
	private void booleanFactor() {
		// (NOT^)? relation
		if (lookAhead(1) == TokenType.NOT) {
			match(TokenType.NOT);
		}
		booleanRelation();
	}
	
	private void booleanRelation() {
		// sumExpr ((LE^ | LT^ | GE^ | GT^ | EQUAL^ | NOT_EQUAL^) sumExpr)?
		sumExpression();
		boolean relation = false;
		if (lookAhead(1) == TokenType.LESS_EQUAL) {
			relation = true;
			match(TokenType.LESS_EQUAL);
		} else if (lookAhead(1) == TokenType.LESS_THEN) {
			relation = true;
			match(TokenType.LESS_THEN);
		} else if (lookAhead(1) == TokenType.GREATER_EQUAL) {
			relation = true;
			match(TokenType.GREATER_EQUAL);
		} else if (lookAhead(1) == TokenType.GREATER_THEN) {
			relation = true;
			match(TokenType.GREATER_THEN);
		} else if (lookAhead(1) == TokenType.EQUAL) {
			relation = true;
			match(TokenType.EQUAL);
		} else if (lookAhead(1) == TokenType.NOT_EQUAL) {
			relation = true;
			match(TokenType.NOT_EQUAL);
		}
		if (relation) {
			sumExpression();
		}
	}
	
	private void stringExpression() {
		// string (CONC^ stringExpr)?
		string();
		if (lookAhead(1) == TokenType.CONCAT) {
			match(TokenType.CONCAT);
			stringExpression();
		}		
	}
	
	private void string() {
		// STRING_LITERAL | boolExpr
		if (lookAhead(1) == TokenType.STRING_LITERAL) {
			match(TokenType.STRING_LITERAL);
		} else {
			booleanExpression();
		}
	}
}
