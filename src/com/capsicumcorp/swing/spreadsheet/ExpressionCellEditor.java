/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JSpread.
 * 
 * JSpread is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * JSpread is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.capsicumcorp.swing.spreadsheet;

import java.awt.Component;
import javax.swing.JTextField;

import net.sf.jeppers.expression.*;
import net.sf.jeppers.grid.*;

/**
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class ExpressionCellEditor extends GenericCellEditor{
	public ExpressionCellEditor(){
		super(new JTextField());
	}
	
	public Component getEditorComponent(
		int row,
		int column,
		Object value,
		CellStyle style,
		boolean isSelected,
		JGrid grid) {
			if(value instanceof Expression){
				value = "=" + ((Expression)value).getExpression();
			}
			return super.getEditorComponent(row, column, value, style, isSelected, grid);
		}
}
