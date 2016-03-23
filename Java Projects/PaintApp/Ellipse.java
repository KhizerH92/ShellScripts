import java.awt.*;

//WORK IN PROGRESS : ARGUMENTS IN CONTAINS POINT:

/**
 * Ellipse.java
 * Class for an ellipse.
 * 
 * @author Tom Cormen
 * @author YOU
 * @see Shape
 */
public class Ellipse extends Shape {
  // YOU FILL IN INSTANCE VARIABLES AND METHODS.
	private int myX, myY;           // x and y coords of Ellipse's upper left corner
	private int myWidth, myHeight;  // Elliptical Rect's width and height 
	private Point t;

	/**
	 * Constructor for Ellipse. Called to create a Ellipse object with upper left
	 * corner (x, y), width "width", height "height", and color EllipseColor.
	 * @param x the x coordinate of the upper left corner
	 * @param y the y coordinate of the upper left corner
	 * @param width the width of the Ellipse
	 * @param height the height of the Ellipse
	 /
	  * 
	  * @param x
	  * @param y
	  * @param width
	  * @param height
	  * @param ellipseColor
	  */
	public Ellipse(int x, int y, int width, int height, Color ellipseColor) {
		super(ellipseColor);
		myX = x;
		myY = y;
		myWidth = width;
		myHeight = height;
		
	}

	/**
	 * Have the Rect object draw itself on the page passed as a parameter.
	 * @param page graphics object to draw on
	 */

	/**
	 * Have the Rect object fill itself on the page passed as a parameter.
	 * @param page graphics object to draw on
	 */
	public void drawShape(Graphics page) {
		// Draw the Ellipse.
		page.fillOval(myX, myY, myWidth, myHeight);
	}

	/**
	 * Have the Ellipse object move itself by deltaX, deltaY.
	 * @param deltaX amount to change x value
	 * @param deltaY amount to change y value
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * Set the x value of the upper left corner of rectangle around ellipse to x
	 * @param x new x value
	 */
	public void setX(int x) {
		myX = x;
	}

	/**
	 * Set the y value of the upper left corner of Rectangular around ellipse to y
	 * @param y new y value
	 */
	public void setY(int y) {
		myY = y;
	}

	/**
	 * @return x value of the upper left corner of ellipse
	 */
	public int getX() {
		return myX;
	}

	/**
	 * @return the y value of the upper left corner of ellipse
	 */
	public int getY() {
		return myY;
	}

	/**
	 * Set the width of the Rect around ellipse to width
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

	public Point getCenter(){
		t = new Point();
		t.x = myX + (int) myWidth/2;
		t.y = myY + (int) myHeight/2;
		return t;
	}

	public boolean containsPoint(Point p){
		return pointInEllipse(p, myX, myY, myWidth, myHeight);
	}
	
	
  // Helper method that returns whether Point p is in an Ellipse with the given
  // top left corner and size.
  private static boolean pointInEllipse(Point p, int left, int top, int width,
      int height) {
    double a = width / 2.0;			// half of the width
    double b = height / 2.0;		// half of the height
    double centerx = left + a;	// x-coord of the center
    double centery = top + b;		// y-coord of the center
    double x = p.x - centerx;		// horizontal distance between p and center
    double y = p.y - centery;		// vertical distance between p and center

    // Now we just apply the standard geometry formula.
    // (See CRC, 29th edition, p. 178.)
    return Math.pow(x / a, 2) + Math.pow(y / b, 2) <= 1;
  }
  
}
