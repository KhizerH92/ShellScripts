import java.awt.*;

/**
 * Segment.java
 * Class for a line segment.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author YOU
 * @see Shape
 */
public class Segment extends Shape {
  // YOU FILL IN INSTANCE VARIABLES AND METHODS.
	int left, right, top, bottom;
	private int myX, myY;
	private int myX2, myY2;
	private int tolerance = 3;
	private Point t;
	
	
	public Segment(int x1, int y1, int x2, int y2, Color SegColor){
		super(SegColor);
		myX = x1;
		myY = y1;
		myX2 = x2;
		myY2 = y2;
	}
	
	public void drawShape(Graphics page) {
		page.drawLine(myX, myY, myX2, myY2);
	}

	/**
	 * Have the Rect object move itself by deltaX, deltaY.
	 * @param deltaX amount to change x value
	 * @param deltaY amount to change y value
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
		myX2 += deltaX;
		myY2 += deltaY;
		
	}
	
	public Point getCenter(){
		t = new Point();
		t.x = (int) ((myX + myX2)/2);
		t.y = (int)((myY+myY2)/2);
		return t;
	}
	

	public boolean containsPoint(Point k){
		if (myX<myX2){
			left = myX;
			right = myX2;
		}
		else{
			left = myX2;
			right = myX;
		}
		if (myY<myY2){
			top = myY;
			bottom = myY2;
			}
		else{
			top = myY2;
			bottom = myY;
		}		
		
		return ((distanceToPoint(k,myX,myY,myX2,myY2)<=tolerance)&&
				almostContainsPoint(k, left, top, right ,bottom ,tolerance));
	}


  // Helper method that returns true if Point p is within a tolerance of a
  // given bounding box. Here, the bounding box is given by the coordinates of
  // its left, top, right, and bottom.
  private static boolean almostContainsPoint(Point p, int left, int top, int right, int bottom, double tolerance){
    return p.x >= left - tolerance && p.y >= top - tolerance
        && p.x <= right + tolerance && p.y <= bottom + tolerance;
  }

  // Helper method that returns the distance from Point p to the line
  // containing a line segment whose endpoints are given.
  private static double distanceToPoint(Point p, int x1, int y1, int x2, int y2) {
    if (x1 == x2) // vertical segment?
      return (double) (Math.abs(p.x - x1)); // yes, use horizontal distance
    else if (y1 == y2) // horizontal segment?
      return (double) (Math.abs(p.y - y1)); // yes, use vertical distance
    else {
      // Here, we know that the segment is neither vertical nor
      // horizontal.
      // Compute m, the slope of the line containing the segment.
      double m = ((double) (y1 - y2)) / ((double) (x1 - x2));

      // Compute mperp, the slope of the line perpendicular to the
      // segment.
      double mperp = -1.0 / m;

      // Compute the (x, y) intersection of the line containing the
      // segment and the line that is perpendicular to the segment and that
      // contains Point p.
      double x = (((double) y1) - ((double) p.y) - (m * x1) + (mperp * p.x))
          / (mperp - m);
      double y = m * (x - x1) + y1;

      // Return the distance between Point p and (x, y).
      return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }
  }
}