/*
 * FormulaTableModel.java
 *
 * Created on 11 July 2002, 14:32
 */

package org.jeppers.swing.spreadsheet;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author  Administrator
 * @version
 */
public class FormulaTableModel extends DefaultTableModel{
    private String blank = "";
    private Double zero = new Double(0);
    protected org.nfunk.jep.JEP expressionParser;
    
    
    /** Creates new FormulaTableModel */
    public FormulaTableModel(int numRows, int numColumns) {
        super(numRows, numColumns);
        expressionParser = new org.nfunk.jep.JEP();
        expressionParser.addStandardFunctions();
        expressionParser.setAllowUndeclared(true);
    }
    
    public Object getValueAt(int row, int column){
        Object obj = super.getValueAt(row, column);
        if(obj == null){
            return obj;
        }
        String value = obj.toString();
        if(value.startsWith("=")){ // Cell is formula
            // parse expression
            expressionParser.initSymTab();
            expressionParser.parseExpression(value.substring(1));
            
            // Resolve variables
            org.nfunk.jep.SymbolTable symbols = expressionParser.getSymbolTable();            
            for(java.util.Enumeration e = symbols.keys() ; e.hasMoreElements() ;){
                Object key = e.nextElement();
                String variable = key.toString();
                
                // Convert variable name into cell coordinates
                int varRow = Integer.parseInt(variable.substring(1)) - 1;
                int varCol = variable.charAt(0) - 'A';      
                
                // Get value of variable
                Object varObject = getValueAt(varRow, varCol);
                
                // Convert to double
                if(varObject == null){
                    varObject = zero;
                }else{
                    try{
                        varObject = Double.valueOf(varObject.toString());
                    }catch(NumberFormatException notDouble){
                        varObject = zero;
                    }
                }
                
                // Add value for variable
                expressionParser.addVariableAsObject(variable, varObject);
            }
            
            obj = expressionParser.getValueAsObject();            
        }
        return obj;
    }    
    
    public void setValueAt(Object aValue, int row, int column){
        super.setValueAt(aValue, row, column);
        if(aValue == null){
            aValue = blank;
        }
     
        //
        // Add cell listener for expression
        //
        String value = aValue.toString();
        if(value.startsWith("=")){ // Cell is formula
            // Parse expression
            String expression = value.substring(1);
            expressionParser.parseExpression(expression);
     
            // Add cell listener for each variable in the expression
            org.nfunk.jep.SymbolTable symbols = expressionParser.getSymbolTable();
            for(java.util.Enumeration e = symbols.keys() ; e.hasMoreElements() ;){
                String variable = e.nextElement().toString();
     
                // Convert variable name into cell coordinates
                int varRow = Integer.parseInt(variable.substring(1)) - 1;
                int varCol = variable.charAt(0) - 'A';                                
     
                // Add cell listener
                addTableModelListener(new CellListener(varRow, varCol, row, column, this));                
            } // end for
            
            expressionParser.initSymTab();
        } // end if
    } // end setValueAt
     
    protected class CellListener implements TableModelListener{
        // row to listen on
        private int listenRow;
        // col to listen on
        private int listenCol;
        // formula row
        private int formulaRow;
        // formula col
        private int formulaCol;
        // model to update
        private FormulaTableModel model;
        
        /* Listen to cell (row, col) */
        public CellListener(int listenRow, int listenCol, int formulaRow, int formulaCol, FormulaTableModel model){
            this.listenRow = listenRow;
            this.listenCol = listenCol;
            this.formulaRow = formulaRow;
            this.formulaCol = formulaCol;
            this.model = model;
        }
        
        public void tableChanged(TableModelEvent e){
            if(listenRow >= e.getFirstRow() && listenRow <= e.getLastRow() && listenCol == e.getColumn()){
                // Formula cell has changed
                TableModelEvent formulaUpdate = new TableModelEvent(model, formulaRow, formulaRow, formulaCol);
                model.fireTableChanged(formulaUpdate);
            }
        }
    }
} // end FormulaTableModel