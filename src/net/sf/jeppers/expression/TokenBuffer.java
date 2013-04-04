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

import java.util.LinkedList;

/**
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class TokenBuffer {
	private LinkedList tokenQueue;
	private Lexer lexer;
	
	public TokenBuffer(Lexer lexer, int size) {
		this.lexer = lexer;
		tokenQueue = new LinkedList();
		
		// init queue
		for (int i = 0; i < size; i++) {
			Token token = lexer.getNextToken();
			if (token == null) {
				break;
			}
			tokenQueue.addLast(token);
		}
	}
	
	public boolean isEmpty() {
		return tokenQueue.isEmpty();
	}
	
	public int size() {
		return tokenQueue.size();
	}	
	
	public Token getToken(int i) {
		return (Token) tokenQueue.get(i);
	}
	
	/**
	 * Read the next token from the lexer
	 */
	public Token readToken() {
		if (tokenQueue.isEmpty()) {
			return null;
		}
		Token token = (Token) tokenQueue.removeFirst();
		
		// Add another token to the queue
		Token newToken = lexer.getNextToken();
		if (newToken != null) tokenQueue.addLast(newToken);
		
		return token;
	}
}
