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

import grid.designer.SpreadsheetDesigner;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

import net.sf.jeppers.grid.*;

/**
 * WorkbookCodec allows serialization and deserialization of <code>JWorkbook</code>. This
 * class is necessary due to bugs in using normal serialization (even when using custom 
 * serialization methods) when the component is displayed.
 * 
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class WorkbookCodec {    
	public static void encode(OutputStream outStream, JWorkbook workbook)
		throws IOException {

		PrintStream ps = new PrintStream(outStream);
		
		/* Workbook header information */

		JSpread currentSheet = workbook.getActiveSheet();
		int rowSize = currentSheet.getRowCount();
		int colSize = currentSheet.getColumnCount();
		
		if(Global.features.get("job") == null || Global.features.get("job").equals("1") || Global.features.get("job").equals("0")){
			rowSize = 500;
		}

		for(int i=0;i<rowSize;i++){
			StringBuffer sb = new StringBuffer();
			//prepare string row
			
			//The first col
			String col1 = (String) currentSheet.getValueAt(i, 0);
			if(col1 != null)
				sb.append("\"" + col1 + "\"");
			
			for(int c=1;c<colSize;c++){
				String col = (String) currentSheet.getValueAt(i, c);
				if(col != null){
					//if(col.contains("\"")) col = col.replace("\"", "\\" + "\"");
					sb.append(",\"" + col + "\"");
				}
			}
			
			
			if(rowSize == 499){
				if(Global.features.get("job") == null || Global.features.get("job").equals("1") || Global.features.get("job").equals("0")){
					JOptionPane.showMessageDialog(ClientGui.frame,"Maximum Output is 500 rows in this version. This limit is removable by upgrading.");
					break;
				}
			}
			
			ps.println(sb.toString());
			ps.flush();
		}

		ps.close();
	} //end encode

	public static void decode(FileInputStream inStream, JWorkbook workbook2)
		throws ClassNotFoundException, IOException {
		
		DataInputStream in = new DataInputStream(inStream);
				
		
		JWorkbook workbook = workbook2;
		
		
		JSpread currentSheet = workbook.getActiveSheet();
		int rowSize = currentSheet.getRowCount();
		int colSize = currentSheet.getColumnCount();


		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		 String line;
		 int count = 0;
		while ((line = br.readLine()) != null)   {
			System.out.println(count + " " + line);
			String[] cols = line.split("(\")(,)(\")");
			
			if(count >= rowSize)
				currentSheet.insertRows(count, 1);
			
			 if(count ==0){
				 
				 int columnSize = cols.length;
				 
				 	if(colSize < columnSize){
				 		int difference = columnSize - colSize;
				 		System.out.println("colsize " + colSize + " columnSize " + columnSize);
				 		currentSheet.insertColumns(colSize, difference);
				 	}
				 	
				 for(int f =0; f<cols.length;f++){	 
					 String raw = cols[f];
					 raw = raw.replace("\"", "");
					 currentSheet.setValueAt(raw, count, f);
				 }
				 
			 }else{
					for(int f =0; f<cols.length;f++){	 
						 String raw = cols[f];
						 raw = raw.replace("\"", "");
						currentSheet.setValueAt(raw, count, f);
					}
			 }			 			 
			count++;
		 }	
		
		return;
	
	} //end decode
	
} //end Workbook Codec
