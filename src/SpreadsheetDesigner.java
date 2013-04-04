import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.Vector;
import java.io.*;

import org.jeppers.swing.spreadsheet.*;

public class SpreadsheetDesigner extends JFrame {
    // File Actions
    private Action actionOpen;
    private Action actionSave;
    private Action actionExit;

    // Format actions
    private Action actionFont;
    private Action actionBorder;
    private Action actionRowHeight;

    // Alignment Actions
    private Action actionLeftAlign;
    private Action actionCenterHorizontalAlign;
    private Action actionRightAlign;
    private Action actionTopAlign;
    private Action actionCenterVerticalAlign;
    private Action actionBottomAlign;

    // Setting color actions
    private Action actionBgColor;
    private Action actionFgColor;

    // span actions
    private Action actionSpan;
    
    // grid action
    private Action actionGrid;
    
    private boolean showGrid = true;

    // size constants
    final static int ROWS = 50;
    final static int COLS = 10;
    
    private JTable table;
    private TableModel              tableModel;
    private DefaultSpanModel        spanModel;
    private DefaultAttributeModel   attributeModel;
    
    public SpreadsheetDesigner() {
        super("Spreadsheet Designer");
        
        //Quit this app when the main window closes
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ExitProgram();
            }
        });
        
        //Create TabbedPane
        JScrollPane scrollPanel;
        
        //Create Actions
        createActions();
        
        //Create the toolbar.
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);
        
        //Create menubar
        setJMenuBar(createMenuBar());
        
        // create table
        tableModel      = new FormulaTableModel(ROWS,COLS);
        spanModel       = new DefaultSpanModel(ROWS,COLS);
        attributeModel  = new DefaultAttributeModel(ROWS,COLS);
        table = new JSpreadsheet(tableModel, spanModel, attributeModel);
        
        scrollPanel = new JScrollPane(table);
        
        // Setup window
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(400, 100));
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(scrollPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        
        // Maximise main window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
    }
    
    private void ExitProgram(){
        System.exit(0);
    }
    
    public static void main(String[] args) {
        SpreadsheetDesigner frame = new SpreadsheetDesigner();
        frame.setVisible(true);
    }
    
    private void createActions(){
        actionOpen = new AbstractAction("Open",
        new ImageIcon("../icons/Open24.gif")) {
            public void actionPerformed(ActionEvent e) {
                //Action code
                openTable();
            }
        };
        
        actionSave = new AbstractAction("Save",
        new ImageIcon("../icons/Save24.gif")) {
            public void actionPerformed(ActionEvent e) {
                //Action code
                saveTable();
            }
        };
        
        actionExit = new AbstractAction("Exit",
        new ImageIcon("../icons/Exit24.gif")) {
            public void actionPerformed(ActionEvent e) {
                ExitProgram();
            }
        };
        
        actionLeftAlign = new AbstractAction("Left",
        new ImageIcon("../icons/AlignLeft24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setHorizontalAlignment(row, column, AttributeModel.LEFT);
                    }
                }
            }
        };
        
        actionCenterHorizontalAlign = new AbstractAction("Center",
        new ImageIcon("../icons/AlignCenter24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setHorizontalAlignment(row, column, AttributeModel.CENTER);
                    }
                }
            }
        };
        
        actionRightAlign = new AbstractAction("Right",
        new ImageIcon("../icons/AlignRight24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setHorizontalAlignment(row, column, AttributeModel.RIGHT);
                    }
                }
            }
        };
        
        actionTopAlign = new AbstractAction("Top",
        new ImageIcon("../icons/AlignTop24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setVerticalAlignment(row, column, SwingConstants.TOP);
                    }
                }
            }
        };
        
        actionCenterVerticalAlign = new AbstractAction("Center",
        new ImageIcon("../icons/AlignCenter24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setVerticalAlignment(row, column, AttributeModel.CENTER);
                    }
                }
            }
        };
        
        actionBottomAlign = new AbstractAction("Bottom",
        new ImageIcon("../icons/AlignBottom24.gif")){
            public void actionPerformed(ActionEvent e){
                //Action code
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                for(int i=0; i < rows.length; i++){
                    int row = rows[i];
                    for(int j=0; j < columns.length; j++){
                        int column = columns[j];
                        attributeModel.setVerticalAlignment(row, column, SwingConstants.BOTTOM);
                    }
                }
            }
        };
        
        actionBgColor = new AbstractAction("Background Color"){
            public void actionPerformed(ActionEvent e){
                changeColor(false);
            }
        };
        
        actionFgColor = new AbstractAction("Foreground Color"){
            public void actionPerformed(ActionEvent e){
                changeColor(true);
            }
        };
        
        actionFont = new AbstractAction("Font..."){
            public void actionPerformed(ActionEvent e){
                FontDialog fontDlg = new FontDialog(new JFrame(), table, attributeModel);
                fontDlg.show();
            }
        };
        
        actionBorder = new AbstractAction("Border"){
            public void actionPerformed(ActionEvent e){
                BorderDialog borderDlg = new BorderDialog(new JFrame(), table, attributeModel);
                borderDlg.show();
            }
        };
        
        actionRowHeight = new AbstractAction("Row Height"){
            public void actionPerformed(ActionEvent e){
                RowHeightDialog rowHeightDlg = new RowHeightDialog(new JFrame(), table);
                rowHeightDlg.show();
            }
        };
        
        actionSpan = new AbstractAction("Span"){
            public void actionPerformed(ActionEvent e){
                int[] columns = table.getSelectedColumns();
                int[] rows    = table.getSelectedRows();
                if ((rows == null) || (columns == null)) return;
                if ((rows.length<1)||(columns.length<1)) return;
                
                int row = rows[0];
                int col = columns[0];
                int rowCount = rows.length;
                int colCount = columns.length;
                spanModel.addSpan(row, col, rowCount, colCount);
            }
        };
        
        actionGrid = new AbstractAction("Grid On/Off"){
            public void actionPerformed(ActionEvent e){
                showGrid = !showGrid;
                table.setShowGrid(showGrid);
            }    
        };    
        
        /*
        actionTemplate = new AbstractAction("Name", new ImageIcon("icon")){
            public void actionPerformed(ActionEvent e){
            }
        };
         */
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;
        
        //create file menu
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        
        menuItem = menu.add(actionOpen);
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setIcon(null); //arbitrarily chose not to use icon in menu
        
        menuItem = menu.add(actionSave);
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setIcon(null);
        
        menu.addSeparator();
        
        menuItem = menu.add(actionExit);
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setIcon(null);
        
        menuBar.add(menu);
        
        //create format menu
        menu = new JMenu("Format");
        menu.setMnemonic(KeyEvent.VK_M);
        
        menuItem = menu.add(actionFgColor);
        menuItem.setMnemonic(KeyEvent.VK_F);
        
        menuItem = menu.add(actionBgColor);
        menuItem.setMnemonic(KeyEvent.VK_B);
        
        menu.addSeparator();
        
        menuItem = menu.add(actionFont);
        
        menuItem = menu.add(actionBorder);
        
        menuItem = menu.add(actionRowHeight);
        
        menu.addSeparator();
        
        menuItem = menu.add(actionSpan);
        
        menu.addSeparator();
        
        menuItem = menu.add(actionGrid);
        
        menuBar.add(menu);
        
        return menuBar;
    }
    
    private void addButtons(JToolBar toolBar) {
        JButton button = null;
        Insets noPadding = new Insets(0,0,0,0);
        
        //open button
        button = toolBar.add(actionOpen);
        button.setToolTipText("Open");
        button.setText("");//an icon-only button
        
        //save button
        button = toolBar.add(actionSave);
        button.setToolTipText("Save");
        button.setText("");
        
        toolBar.addSeparator();
        
        // alignment buttons
        button = toolBar.add(actionLeftAlign);
        button = toolBar.add(actionCenterHorizontalAlign);
        button = toolBar.add(actionRightAlign);
        toolBar.addSeparator();
        button = toolBar.add(actionTopAlign);
        button = toolBar.add(actionCenterVerticalAlign);
        button = toolBar.add(actionBottomAlign);
    }
    
    private void changeColor(boolean isForeground) {
        int[] columns = table.getSelectedColumns();
        int[] rows    = table.getSelectedRows();
        if ((rows == null) || (columns == null)) return;
        if ((rows.length<1)||(columns.length<1)) return;
        int row = rows[0];
        int column = columns[0];
        
        Color target    = null;
        Color reference = null;
        
        String title;
        if (isForeground) {
            target = table.getForeground();
            reference = table.getBackground();
            title = "Foreground Color";
        } else {
            target = table.getBackground();
            reference = table.getForeground();
            title = "Background Color";
        }
        TextColorChooser chooser =  new TextColorChooser(target, reference, isForeground);
        Color color = chooser.showDialog(SpreadsheetDesigner.this,title);
        if (color != null) {
            for(int i=0; i<rows.length; i++){
                row = rows[i];
                for(int j=0; j<columns.length; j++){
                    column = columns[j];
                    if (isForeground) {
                        attributeModel.setForeground(row, column, color);
                    } else {
                        attributeModel.setBackground(row, column, color);
                    }
                }
            }
        }
    }
    
    private void saveTable(){
        try{
            File outputFile = new File("../data/spread.dat");
            FileOutputStream fileOut = new FileOutputStream(outputFile);
            BufferedOutputStream buffOut = new BufferedOutputStream(fileOut);
            DataOutputStream out = new DataOutputStream(buffOut);
            buffOut = null;
            fileOut = null;
            
            //
            String blank = "";
            
            // Write out header information
            out.writeInt(table.getRowCount());
            out.writeInt(table.getColumnCount());
            
            // Save size information
            for(int row=0; row < table.getRowCount(); row++){
                int rowHeight = table.getRowHeight(row);
                out.writeInt(rowHeight);
            }
            for(int col=0; col < table.getColumnCount(); col++){
                int colWidth = table.getColumnModel().getColumn(col).getWidth();
                out.writeInt(colWidth);
            }
            
            // Save table contents
            for(int row=0; row < table.getRowCount(); row++){
                for(int col=0; col < table.getColumnCount(); col++){
                    Object value = table.getValueAt(row, col);
                    if(value == null){
                        value = blank;
                    }
                    Color fgColor = attributeModel.getForeground(row, col);
                    Color bgColor = attributeModel.getBackground(row, col);
                    int horizontalAlign = attributeModel.getHorizontalAlignment(row, col);
                    int verticalAlign = attributeModel.getVerticalAlignment(row, col);
                    Font font = attributeModel.getFont(row, col);
                    CellSpan span = spanModel.getSpanAt(row, col);
                    Border border = attributeModel.getBorder(row, col);
                    Insets borderInsets = border.getBorderInsets(table);
                    
                    out.writeUTF(value.toString());
                    out.writeInt(fgColor.getRGB());
                    out.writeInt(bgColor.getRGB());
                    out.writeInt(horizontalAlign);
                    out.writeInt(verticalAlign);
                    out.writeUTF(font.getFontName());
                    out.writeInt(font.getStyle());
                    out.writeInt(font.getSize());
                    out.writeInt(span.getRow());
                    out.writeInt(span.getColumn());
                    out.writeInt(span.getRowCount());
                    out.writeInt(span.getColumnCount());
                    out.writeInt(borderInsets.top);
                    out.writeInt(borderInsets.left);
                    out.writeInt(borderInsets.bottom);
                    out.writeInt(borderInsets.right);                    
                }
            }
            
            out.close();
        }catch(IOException ioException){
            // unable to save file
        }
    }
    
    private void openTable(){
        try{
            File inFile = new File("../data/spread.dat");
            FileInputStream fileIn = new FileInputStream(inFile);
            BufferedInputStream buffIn = new BufferedInputStream(fileIn);
            DataInputStream in = new DataInputStream(buffIn);
            buffIn = null;
            fileIn = null;
            
            // Read in header information
            int noRows = in.readInt();
            int noCols = in.readInt();
            
            // initialise table
            tableModel = new FormulaTableModel(noRows, noCols);
            spanModel  = new DefaultSpanModel(noRows, noCols);
            attributeModel = new DefaultAttributeModel(noRows, noCols);
            JSpreadsheet spread = (JSpreadsheet)table;
            spread.setModel(tableModel);
            spread.setSpanModel(spanModel);
            spread.setAttributeModel(attributeModel);          
            
            Color borderColor = Color.black;
            
            // read size information
            for(int row=0; row < noRows; row++){
                int rowHeight = in.readInt();                    
                table.setRowHeight(row, rowHeight);
            }
            for(int col=0; col < noCols; col++){
                int colWidth = in.readInt();
                table.getColumnModel().getColumn(col).setPreferredWidth(colWidth);
            }
            
            // read contents
            for(int row=0; row < noRows; row++){
                for(int col=0; col < noCols; col++){
                    String value = in.readUTF();
                    Color fgColor = new Color(in.readInt());
                    Color bgColor = new Color(in.readInt());
                    int horizontalAlign = in.readInt();
                    int verticalAlign = in.readInt();
                    Font font = new Font(in.readUTF(), in.readInt(), in.readInt());
                                        int spanRow = in.readInt();
                    int spanCol = in.readInt();
                    int spanRowCount = in.readInt();
                    int spanColCount = in.readInt();                    
                    Insets borderInsets = new Insets(in.readInt(), in.readInt(), in.readInt(), in.readInt());
                    LinesBorder border = new LinesBorder(borderColor, borderInsets);
                    
                    table.setValueAt(value, row, col);
                    attributeModel.setForeground(row, col, fgColor);
                    attributeModel.setBackground(row, col, bgColor);
                    attributeModel.setHorizontalAlignment(row, col, horizontalAlign);
                    attributeModel.setVerticalAlignment(row, col, verticalAlign);
                    attributeModel.setFont(row, col, font);
                    spanModel.addSpan(spanRow, spanCol, spanRowCount, spanColCount);
                    attributeModel.setBorder(row, col, border);
                }
            }
            
            in.close();
        }catch(IOException ioException){
            // Unable to open file
        }
    }    
}
