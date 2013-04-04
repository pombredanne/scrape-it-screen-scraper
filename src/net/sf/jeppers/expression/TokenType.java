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
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class TokenType {
	final static public TokenType PLUS = new TokenType("PLUS");
	final static public TokenType MINUS = new TokenType("MINUS");
	final static public TokenType MULTIPLY = new TokenType("MULTIPLY");
	final static public TokenType DIVIDE = new TokenType("DIVIDE");
	final static public TokenType MOD = new TokenType("MOD");
	final static public TokenType POWER = new TokenType("POWER");
	final static public TokenType LPAREN = new TokenType("LPAREN");
	final static public TokenType RPAREN = new TokenType("RPAREN");
	final static public TokenType COMMA = new TokenType("COMMA");
	final static public TokenType CONCAT = new TokenType("CONCAT");
	final static public TokenType NOT = new TokenType("NOT");
	final static public TokenType AND = new TokenType("AND");
	final static public TokenType OR = new TokenType("OR");
	final static public TokenType LESS_EQUAL = new TokenType("LESS_EQUAL");
	final static public TokenType LESS_THEN = new TokenType("LESS_THEN");
	final static public TokenType GREATER_EQUAL = new TokenType("GREATER_EQUAL");
	final static public TokenType GREATER_THEN = new TokenType("GREATER_THEN");
	final static public TokenType EQUAL = new TokenType("EQUAL");
	final static public TokenType NOT_EQUAL = new TokenType("NOT_EQUAL");
	final static public TokenType SIGN_MINUS = new TokenType("SIGN_MINUS");
	final static public TokenType SIGN_PLUS = new TokenType("SIGN_PLUS");
	final static public TokenType NUM = new TokenType("NUM");
	final static public TokenType STRING_LITERAL = new TokenType("STRING_LITERAL");
	final static public TokenType VAR = new TokenType("VAR");
	final static public TokenType METHOD_CALL = new TokenType("METHOD_CALL");
	
	static public boolean isOperand(Token token) {
		if (token.getType() == TokenType.NUM ||
			token.getType() == TokenType.STRING_LITERAL ||
			token.getType() == TokenType.VAR) {
			return true;
		}
		return false;
	}
	
	static public boolean isUnary(Token token) {
		if (token.getType() == TokenType.NOT ||
			token.getType() == TokenType.SIGN_PLUS ||
			token.getType() == TokenType.SIGN_MINUS) {
			return true;
		}
		return false;
	}
	
	static public int precendenceLevel(TokenType type) {
		if (type == TokenType.COMMA) {
			return 0;
		} else if (type == TokenType.OR) {
			return 1;
		} else if (type == TokenType.AND) {
			return 2;
		} else if (type == TokenType.NOT) {
			return 3;
		} else if (type == TokenType.LESS_THEN || 
				type == TokenType.LESS_EQUAL || 
				type == TokenType.GREATER_THEN || 
				type == TokenType.GREATER_EQUAL || 
				type == TokenType.EQUAL	|| 
				type == TokenType.NOT_EQUAL) {
			return 4;
		} else if (type == TokenType.PLUS || 
				type == TokenType.MINUS) {
			return 5;
		} else if (type == TokenType.MULTIPLY || 
				type == TokenType.DIVIDE || 
				type == TokenType.MOD) {
			return 6;
		} else if (type == TokenType.POWER) {
			return 7;
		} else if (type == TokenType.SIGN_MINUS || 
				type == TokenType.SIGN_PLUS) {
			return 8;
		} else if (type == TokenType.CONCAT) {
			return 9;
		} else if (type == TokenType.LPAREN || 
				type == TokenType.RPAREN) {
			return 10;
		} else if (type == TokenType.METHOD_CALL) {
			return 11;
		}
		throw new UnknownTokenException(type);
	}	
	
	private String tokenName;
	
	private TokenType(String name) {
		tokenName = name;
	}
	
	public String toString() {
		return tokenName;
	}
}
