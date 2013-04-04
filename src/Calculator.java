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
import net.sf.jeppers.expression.*;
import net.sf.jeppers.expression.function.*;

import java.io.*;

/**
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class Calculator {
	final static private String VAR_COMMAND = "#var";
	final static private String QUIT_COMMAND = "#quit";
	
	static private ExpressionCompiler compiler = new ExpressionCompiler();
	
	static public void main(String[] args) {
		compiler.setFunction("if", new If());
		compiler.setFunction("avg", new Average());
		
		System.out.println("Type #quit to exit");
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	        String str = "";
	        while (str != null) {
	            System.out.print("> ");
	            str = in.readLine();
	            if (str.equalsIgnoreCase(QUIT_COMMAND)) {
	            	break;
	            } else if (str.startsWith(VAR_COMMAND)) {
	            	handleVarCommand(str);
	            } else { 
	            	System.out.println(str + " = " + compiler.evaluate(str));
	            }
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	static private void handleVarCommand(String str) {
    	int assignIndex = str.indexOf('='); 
    	if (assignIndex == -1 || assignIndex == str.length() - 1) {
    		System.out.println("Usage: #var [name]=[expression]");
    		return;
    	}
    	
    	String var = str.substring(VAR_COMMAND.length() + 1, assignIndex).trim();
    	if ("".equals(var)) {
    		System.out.println("Usage: #var [name]=[expression]");
    		return;
    	}
    	
    	String expr = str.substring(assignIndex+1);
    	Object value = compiler.evaluate(expr);
    	compiler.setVariable(var, value);
    	System.out.println(var + " = " + value);
	}
}
