import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LinesBorder extends AbstractBorder implements SwingConstants {
    protected int northThickness;
    protected int southThickness;
    protected int eastThickness;
    protected int westThickness;
    protected Color northColor;
    protected Color southColor;
    protected Color eastColor;
    protected Color westColor;
    
    public LinesBorder(Color color) {
        this(color, 1);
    }
    
    public LinesBorder(Color color, int thickness)  {
        setColor(color);
        setThickness(thickness);
    }
    
    public LinesBorder(Color color, Insets insets)  {
        setColor(color);
        setThickness(insets);
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();
        
        // Top edge
        g.setColor(northColor);       
        for(int i=0; i < northThickness; i++){
            g.drawLine(x, y + i, x + width, y + i);
        }
        // Left edge
        g.setColor(westColor);
        for(int i=0; i < westThickness; i++){
            g.drawLine(x + i, y, x + i, y + height);
        }
        // Bottom edge
        g.setColor(southColor);
        for(int i=0; i < southThickness; i++){
            g.drawLine(x, y + height - i, x + width, y + height - i);
        }
        // Right edge
        g.setColor(westColor);        
        for(int i=0; i < eastThickness; i++){
            g.drawLine(x + width - i, y, x + width - i, y + height);
        }        
        
        g.setColor(oldColor);
    }
    
    public Insets getBorderInsets(){
        return new Insets(northThickness, westThickness, southThickness, eastThickness);
    }
    
    public Insets getBorderInsets(Component c)       {
        return new Insets(northThickness, westThickness, southThickness, eastThickness);
    }
    
    public Insets getBorderInsets(Component c, Insets insets) {
        return new Insets(northThickness, westThickness, southThickness, eastThickness);
    }
    
    
    public boolean isBorderOpaque() { return true; }
    
    public void setColor(Color c) {
        northColor = c;
        southColor = c;
        eastColor  = c;
        westColor  = c;
    }
    
    public void setColor(Color c, int direction) {
        switch (direction) {
            case NORTH: northColor = c; break;
            case SOUTH: southColor = c; break;
            case EAST:  eastColor  = c; break;
            case WEST:  westColor  = c; break;
            default:
        }
    }
    
    public void setThickness(int n) {
        northThickness = n;
        southThickness = n;
        eastThickness  = n;
        westThickness  = n;
    }
    
    public void setThickness(Insets insets) {
        northThickness = insets.top;
        southThickness = insets.bottom;
        eastThickness  = insets.right;
        westThickness  = insets.left;
    }
    
    public void setThickness(int n, int direction) {
        switch (direction) {
            case NORTH: northThickness = n; break;
            case SOUTH: southThickness = n; break;
            case EAST:  eastThickness  = n; break;
            case WEST:  westThickness  = n; break;
            default:
        }
    }
    
    public void append(LinesBorder b, boolean isReplace) {
        if (isReplace) {
            northThickness = b.northThickness;
            southThickness = b.southThickness;
            eastThickness  = b.eastThickness;
            westThickness  = b.westThickness;
        } else {
            northThickness = Math.max(northThickness ,b.northThickness);
            southThickness = Math.max(southThickness ,b.southThickness);
            eastThickness  = Math.max(eastThickness  ,b.eastThickness);
            westThickness  = Math.max(westThickness  ,b.westThickness);
        }
    }
    
    public void append(Insets insets, boolean isReplace) {
        if (isReplace) {
            northThickness = insets.top;
            southThickness = insets.bottom;
            eastThickness  = insets.right;
            westThickness  = insets.left;
        } else {
            northThickness = Math.max(northThickness ,insets.top);
            southThickness = Math.max(southThickness ,insets.bottom);
            eastThickness  = Math.max(eastThickness  ,insets.right);
            westThickness  = Math.max(westThickness  ,insets.left);
        }
    }
    
}
