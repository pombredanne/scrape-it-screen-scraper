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
public class Token {
	private TokenType type;
	private String text;
	private int noArguments; // only used by method call tokens
	
	public Token(TokenType type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public TokenType getType() {
		return type;
	}
	
	protected void setType(TokenType type) {
		this.type = type;
	}
	
	/** Only applicable for TokenType.METHOD_CALL **/
	public int getNumberOfArguments() {
		return noArguments;
	}
	
	protected void setNumberOfArguments(int noArguments) {
		this.noArguments = noArguments;
	}
	
	public String getText() {
		return text;
	}
	
	protected void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		return type + ": " + text;
	}
}
