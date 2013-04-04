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

/**
 * This class converts an expression from a <code>String</code> 
 * into <code>Token</code> objects.
 * 
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class Lexer {
	private String in;
	private int currentIndex = 0;
	
	public Lexer(String str) {
		in = str;
	}
	
	private void error(String message) {
		throw new ParseException(message, currentIndex+1);
	}
	
	private void skip() {
		currentIndex++;
	}
	
	private char lookAhead(int i) {
		int index = currentIndex + i - 1;
		if (index >= in.length()) {
			return '\n';
		}
		return in.charAt(index);
	}
	
	private void match(char c) {
		if (lookAhead(1) == c) {
			currentIndex++;
		} else {
			error("Expected '" + c + "' but got '" + lookAhead(1) + "'");
		}
	}
	
	private void match(String str) {
		for (int i = 0; i < str.length(); i++) {
			match(str.charAt(i));
		}
	}
	
	public Token getNextToken() {
		Token token = null;
		// skip whitespace
		while (lookAhead(1) == ' ' || lookAhead(1) == '\t') {
			skip();
		}
		switch (lookAhead(1)) {
		case '+': {
			token = matchPlus();
			break;
		}
		case '-': {
			token = matchMinus();
			break;
		}
		case '*': {
			token = matchMultiply();
			break;
		}
		case '/': {
			token = matchDivide();
			break;
		}
		case '%': {
			token = matchMod();
			break;
		}
		case '^': {
			token = matchPower();
			break;
		}
		case ',': {
			token = matchComma();
			break;
		}
		case '(': {
			token = matchLParen();
			break;
		}
		case ')': {
			token = matchRParen();
			break;
		}
		case '|': {
			token = matchOr();
			break;
		}
		case '=': {
			token = matchEqual();
			break;
		}
		case '&': {
			if (lookAhead(2) == '&') {
				token = matchAnd();
			} else {
				token = matchConcat();
			}
			break;
		}
		case '!': {
			if (lookAhead(2) == '=') {
				token = matchNotEqual();
			} else {
				token = matchNot();
			}
			break;
		}
		case '<': {
			if (lookAhead(2) == '=') {
				token = matchLessEqual(); 
			} else {
				token = matchLessThen();
			}
			break;
		}
		case '>': {
			if (lookAhead(2) == '=') {
				token = matchGreaterEqual();
			} else {
				token = matchGreaterThen();
			}
			break;
		}
		case '"': {
			token = matchLiteral();
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9': {
			token = matchNum();
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':  case '_':  case 'a':
		case 'b':  case 'c':  case 'd':  case 'e':
		case 'f':  case 'g':  case 'h':  case 'i':
		case 'j':  case 'k':  case 'l':  case 'm':
		case 'n':  case 'o':  case 'p':  case 'q':
		case 'r':  case 's':  case 't':  case 'u':
		case 'v':  case 'w':  case 'x':  case 'y':
		case 'z': {
			token = matchVar();
			break;
		}
		}
		if (token == null && lookAhead(1) != '\n') {
			error("Unexpected '" + lookAhead(1) + "' character");
		}
		return token;
	}	
	
	private Token matchPlus() {
		match('+');
		return new Token(TokenType.PLUS, "+");
	}
	
	private Token matchMinus() {
		match('-');
		return new Token(TokenType.MINUS, "-");
	}
	
	private Token matchMultiply() {
		match('*');
		return new Token(TokenType.MULTIPLY, "*");
	}
	
	private Token matchDivide() {
		match('/');
		return new Token(TokenType.DIVIDE, "/");
	}
	
	private Token matchMod() {
		match('%');
		return new Token(TokenType.MOD, "%");
	}
	
	private Token matchPower() {
		match('^');
		return new Token(TokenType.POWER, "^");
	}
	
	private Token matchComma() {
		match(',');
		return new Token(TokenType.COMMA, ",");
	}
	
	private Token matchLParen() {
		match('(');
		return new Token(TokenType.LPAREN, "(");
	}
	
	private Token matchRParen() {
		match(')');
		return new Token(TokenType.RPAREN, ")");
	}
	
	private Token matchOr() {
		match("||");
		return new Token(TokenType.OR, "||");
	}
	
	private Token matchAnd() {
		match("&&");
		return new Token(TokenType.AND, "&&");
	}
	
	private Token matchConcat() {
		match('&');
		return new Token(TokenType.CONCAT, "&");
	}
	
	private Token matchLessEqual() {
		match("<=");
		return new Token(TokenType.LESS_EQUAL, "<=");
	}
	
	private Token matchLessThen() {
		match('<');
		return new Token(TokenType.LESS_THEN, "<");
	}
	
	private Token matchGreaterEqual() {
		match(">=");
		return new Token(TokenType.GREATER_EQUAL, ">=");
	}
	
	private Token matchGreaterThen() {
		match('>');
		return new Token(TokenType.GREATER_THEN, ">");
	}
	
	private Token matchEqual() {
		match('=');
		return new Token(TokenType.EQUAL, "=");
	}
	
	private Token matchNotEqual() {
		match("!=");
		return new Token(TokenType.NOT_EQUAL, "!=");
	}
	
	private Token matchNot() {
		match('!');
		return new Token(TokenType.NOT, "!");
	}
	
	private Token matchNum() {
		int startIndex = currentIndex;
		boolean decimal = false;
		while ((lookAhead(1) >= '0' && lookAhead(1) <= '9') || lookAhead(1) == '.') {
			if (decimal && lookAhead(1) == '.') {
				error("Unexcepted '.' character");
			} else if (lookAhead(1) == '.') {
				decimal = true;
			}
			currentIndex++;			
		}
		int endIndex = currentIndex;
		String num = in.substring(startIndex, endIndex);
		return new Token(TokenType.NUM, num);
	}
	
	private Token matchVar() {
		int startIndex = currentIndex;
		while ((lookAhead(1) >= 'a' && lookAhead(1) <= 'z') || 
				(lookAhead(1) >= 'A' && lookAhead(1) <= 'Z') ||				
				(lookAhead(1) >= '0' && lookAhead(1) <= '9') ||
				lookAhead(1) == '_' || lookAhead(1) == '!') {
			currentIndex++;			
		}
		int endIndex = currentIndex;
		String var = in.substring(startIndex, endIndex);		
		return new Token(TokenType.VAR, var);
	}
	
	private Token matchLiteral() {
		match('"');
		int startIndex = currentIndex;
		do  {
			currentIndex++;
		} while (lookAhead(1) != '"');		
		int endIndex = currentIndex;
		match('"');
		String literal = in.substring(startIndex, endIndex);
		return new Token(TokenType.STRING_LITERAL, literal);
	}
}
