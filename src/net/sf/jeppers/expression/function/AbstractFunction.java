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
package net.sf.jeppers.expression.function;

import java.util.*;


/** 
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public abstract class AbstractFunction implements Function {
	/**
	 * Checks the number of arguments
	 */
	protected void checkArgumentList(List argList) throws InvalidArguments {
		int reqArgs = getNumberOfArguments();
		if (reqArgs != -1 && reqArgs != argList.size()) {
			throw new InvalidArguments(reqArgs);
		}
	}

	/**
	 * Returns the number of required arguments, or -1 if any number of
	 * arguments is allowed.
	 */
	abstract public int getNumberOfArguments();
}
