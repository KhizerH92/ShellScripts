import java.awt.*;

/**
 * Rect.java
 * Class for a rectangle.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author YOU
 * @see Shape
 */
public class Rect extends Shape {
  // YOU FILL IN INSTANCE VARIABLES AND METHODS.
	private int myX, myY;
	private int myHeight, myWidth;
	private Point t;
	
	
	public Rect(int x, int y, int width, int height, Color RectColor){
		super(RectColor);
		myX = x;
		myY = y;
		myWidth = width;
		myHeight = height;
	}
	
	public void drawShape(Graphics page) {
		// Draw the Rectangle.
		page.fillRect(myX, myY, myWidth, myHeight);
	}

	/**
	 * Have the Rect object move itself by deltaX, deltaY.
	 * @param deltaX amount to change x value
	 * @param deltaY amount to change y value
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * Set the x value of the upper left corner of Rect to x
	 * @param x new x value
	 */
	public void setX(int x) {
		myX = x;
	}

	/**
	 * Set the y value of the upper left corner of Rect to y
	 * @param y new y value
	 */
	public void setY(int y) {
		myY = y;
	}

	/**
	 * @return x value of the upper left corner of Rect
	 */
	public int getX() {
		return myX;
	}

	/**
	 * @return the y value of the upper left corner of Rect
	 */
	public int getY() {
		return myY;
	}

	/**
	 * Set the width of the Rect to width
	 * @param width the new width
	 */
	public void setWidth(int width) {
		myWidth = width;
	}
	
	/**
	 * Set the height of the Rect to height
	 * @param height the new height
	 */
	public void setHeight(int height) {
		myHeight = height;
	}

	/**
	 * Set the color of the rect to clr
	 * @param clr the new color
	 */
	/**
	 * Shrink a rectangle by amount in both directions.
	 * @param amount additive amount by which to shrink the rectangle
	 */
	
	public Point getCenter(){
		t = new Point();
		t.x = myX + (int) myWidth/2;
		t.y = myY + (int) myHeight/2;
		return t;
	}

	public boolean containsPoint(Point p){
		if (p.x>= myX && p.x< myX+myWidth && p.y >= myY && p.y< myY+myHeight){
			return true;
		}
		return false;
	}
	
}
