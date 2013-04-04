/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JGrid.
 * 
 * JGrid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * JGrid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.capsicumcorp.swing.border;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.Serializable;

/**
 * LinesBorder is a <code>Border</code> that allows each edge to have different thickness
 * and color.
 * 
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class LinesBorder implements Border, Serializable {
	private Insets thickness = new Insets(0, 0, 0, 0);
	private Color topColor = Color.black;
	private Color leftColor = Color.black;
	private Color bottomColor = Color.black;
	private Color rightColor = Color.black;

	/**
	 * Create an empty black border.
	 */
	public LinesBorder(){
	}

	public LinesBorder(
		int topThickness,
		int leftThickness,
		int bottomThickness,
		int rightThickness,
		Color topColor,
		Color leftColor,
		Color bottomColor,
		Color rightColor) {
			thickness.top = topThickness;
			thickness.left = leftThickness;
			thickness.bottom = bottomThickness;
			thickness.right = rightThickness;
			this.topColor = topColor;
			this.leftColor = leftColor;
			this.bottomColor = bottomColor;
			this.rightColor = rightColor;
	} //end LinesBorder constructor

	/**
	 * Returns the insets of the border.
	 */
	public Insets getBorderInsets(Component c) {
		return thickness;
	} //end getBorderInsets

	/**
	* Return the thickness of an edge
	*/
	public int getThickness(int edge) {
		if (edge == SwingConstants.TOP) {
			return thickness.top;
		}
		if (edge == SwingConstants.LEFT) {
			return thickness.left;
		}
		if (edge == SwingConstants.BOTTOM) {
			return thickness.bottom;
		}
		if (edge == SwingConstants.RIGHT) {
			return thickness.right;
		}
		return 0; //should not get here
	} //end getThickness

	/**
	 * Set the thickness of an edge
	 */
	public void setThickness(int edge, int lineThickness) {
		if (edge == SwingConstants.TOP) {
			thickness.top = lineThickness;
		}
		if (edge == SwingConstants.LEFT) {
			thickness.left = lineThickness;
		}
		if (edge == SwingConstants.BOTTOM) {
			thickness.bottom = lineThickness;
		}
		if (edge == SwingConstants.RIGHT) {
			thickness.right = lineThickness;
		}
	} //end setThickness

	/**
	 * Return the color of a border edge
	 */
	public Color getColor(int edge) {
		if (edge == SwingConstants.TOP) {
			return topColor;
		}
		if (edge == SwingConstants.LEFT) {
			return leftColor;
		}
		if (edge == SwingConstants.BOTTOM) {
			return bottomColor;
		}
		if (edge == SwingConstants.RIGHT) {
			return rightColor;
		}
		return Color.black; //should not get here
	} //end getColor

	/**
	 * Set the color of an edge
	 */
	public void setColor(int edge, Color color) {
		if (edge == SwingConstants.TOP) {
			topColor = color;
		}
		if (edge == SwingConstants.LEFT) {
			leftColor = color;
		}
		if (edge == SwingConstants.BOTTOM) {
			bottomColor = color;
		}
		if (edge == SwingConstants.RIGHT) {
			rightColor = color;
		}
	} //end setColor

	/**
	 * Returns whether or not the border is opaque.
	 */
	public boolean isBorderOpaque() {
		return true;
	} //end isBorderOpaque

	/**
	 * Paints the border for the specified component with the specified position and size.
	 */
	public void paintBorder(
		Component c,
		Graphics g,
		int x,
		int y,
		int width,
		int height) {		
		// Top edge
		g.setColor(topColor);
		for (int i = 0; i < thickness.top; i++) {
			g.drawLine(x, y + i, x + width, y + i);
		}
		// Left edge
		g.setColor(leftColor);
		for (int i = 0; i < thickness.left; i++) {
			g.drawLine(x + i, y, x + i, y + height);
		}
		// Bottom edge
		g.setColor(bottomColor);
		for (int i = 0; i < thickness.bottom; i++) {
			g.drawLine(x, y + height - i, x + width, y + height - i);
		}
		// Right edge       
		g.setColor(rightColor);
		for (int i = 0; i < thickness.right; i++) {
			g.drawLine(x + width - i, y, x + width - i, y + height);
		}
	} //end paintBorder
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (! (obj instanceof LinesBorder)) {
            return false;
        }
        LinesBorder b = (LinesBorder) obj;
        return (topColor == b.topColor ? true : topColor.equals(b.topColor))
                && (bottomColor == b.bottomColor ? true :
                        bottomColor.equals(b.bottomColor))
                && (leftColor == b.leftColor ? true :
                        leftColor.equals(b.leftColor))
                && (rightColor == b.rightColor ? true :
                        rightColor.equals(b.rightColor))
                && (thickness == b.thickness ? true :
                        thickness.equals(b.thickness));
    }
    
    public int hashCode() {
        // Computed as per "Effective Java" book
        int result = 17;
        result = 37 * result + topColor.hashCode();
        result = 37 * result + bottomColor.hashCode();
        result = 37 * result + leftColor.hashCode();
        result = 37 * result + rightColor.hashCode();
        result = 37 * result + thickness.hashCode();
        return result;
    }
} //end LinesBorder class
