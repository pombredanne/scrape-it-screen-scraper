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

import net.sf.jeppers.expression.function.Function;

/**
 * This class compiles expressions into <code>Expression</code> objects.
 * 
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class ExpressionCompiler {
	private Map variableMap = new HashMap();
	private Map functionMap = new HashMap();
	
	public void setVariable(String variableName, Object value) {
		variableMap.put(variableName, value);
	}
	
	public void setFunction(String functionName, Function func) {
		functionMap.put(functionName, func);
	}
	
	/**
	 * Compile <code>expression</code> into an Expression object.
	 * Note variables and functions can be changed after compiling by calling
	 * the <code>setVariable</code> and <code>setFunction</code> methods
	 * respectively on the <code>ExpressionCompiler</code> used to compile
	 * the expression.
	 * @param expression Expression to compile
	 * @return Compiled expression
	 */
	public Expression compile(String expression) {
		Lexer lexer = new Lexer(expression);
		Parser parser = new Parser(lexer);
		Token[] postfixExpression = parser.postfix();
		List references = parser.getReferences();
		return new Expression(
				expression, 				
				postfixExpression,
				references,
				variableMap,
				functionMap);
	}
	
	/**
	 * Evaluate the <code>expression</code>. This method is a convience
	 * for one-time evaluation of expressions.
	 * @param expression Expression to evaluate
	 * @return Result of computing the expression 
	 */
	public Object evaluate(String expression) {
		return compile(expression).evaluate();
	}
}
