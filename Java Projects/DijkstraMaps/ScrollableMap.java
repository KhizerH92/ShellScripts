/**

 * ScrollableMap.java

 * Class for a scrollable roadmap that responds to user actions.

 * For CS 10 Lab Assignment 4.

 * 

 * @author Yu-Han Lyu, Tom Cormen, and YOU

 */



import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.datastructures.*;



@SuppressWarnings("unused")
public class ScrollableMap extends JLabel implements Scrollable,

    MouseMotionListener, MouseListener {
	
  private static final long serialVersionUID = 1L;

  // The first two instance variables are independent of our roadmap application.

  private int maxUnitIncrement = 1;         // increment for scrolling by dragging

  private boolean missingPicture = false;   // do we have an image to display?

  

  private JLabel infoLabel;                 // where to display the result, in words

  private JButton destButton;               // the destination button, so that it can be enabled

  private RoadMap roadmap;                  // the roadmap

  // ADD OTHER INSTANCE VARIABLES AS NEEDED.
  private Vertex<City> source;
  private Vertex<City> destination;
  private boolean findingsource;
  private boolean willUseDistance;
  private Map<Vertex<City>, Vertex<City>> mapToDraw;

  /**

   * Constructor.

   * @param i the highway roadmap image

   * @param m increment for scrolling by dragging

   * @param infoLabel where to display the result

   * @param destButton the destination button

   * @param roadmap the RoadMap object, a graph

   */

  public ScrollableMap(ImageIcon i, int m, JLabel infoLabel, JButton destButton, RoadMap roadmap) {

    super(i);

    if (i == null) {

      missingPicture = true;

      setText("No picture found.");

      setHorizontalAlignment(CENTER);

      setOpaque(true);

      setBackground(Color.white);

    }

    maxUnitIncrement = m;

    this.infoLabel = infoLabel;

    this.destButton = destButton;

    this.roadmap = roadmap;
    
    this.source = null;
    
    this.destination = null;
    
    willUseDistance = true;



    // Let the user scroll by dragging to outside the window.

    setAutoscrolls(true);         // enable synthetic drag events

    addMouseMotionListener(this); // handle mouse drags

    addMouseListener(this);

    this.requestFocus();

    

    findSource();     // start off by having the user click a source city

  }



  // Methods required by the MouseMotionListener interface:

  @Override

  public void mouseMoved(MouseEvent e) { }



  @Override

  public void mouseDragged(MouseEvent e) {

    // The user is dragging us, so scroll!

    Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);

    scrollRectToVisible(r);

  }



  // Draws the map and shortest paths, as appropriate.

  // If shortest paths have been computed, draws either the entire shortest-path tree

  // or just a shortest path from the source vertex to the destination vertex.

  @Override

  public void paintComponent(Graphics page) {

    Graphics2D page2D = (Graphics2D) page;

    setRenderingHints(page2D);

    super.paintComponent(page2D);

    Stroke oldStroke = page2D.getStroke();  // save the current stroke

    page2D.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_BUTT,

        BasicStroke.JOIN_MITER));

    

    // YOU FILL IN THIS PART.

    // If shortest paths have been computed and there is a destination vertex, draw

    // a shortest path from the source vertex to the destination vertex.

    // If shortest paths have been computed and there is not a destination vertex,

    // draw the entire shortest-path tree.

    // If shortest paths have not been computed, draw nothing.
    
    
	if(mapToDraw != null){ //if shortest path has been computed
		Set<Vertex<City>> keyset = mapToDraw.keySet(); //get keyset to map the shortest routes
		if(destination == null){ //if no desitnation has been selected 
    	 
			for(Vertex<City> c: keyset){ //connect all the cities to their predecessors
				int x1 = c.getElement().getLocal().x; 
				int y1 = c.getElement().getLocal().y;
				int x2 = mapToDraw.get(c).getElement().getLocal().x; //predecessors point
				int y2 = mapToDraw.get(c).getElement().getLocal().y;
    		 
    		 
				page2D.drawLine(x1, y1, x2, y2); //draw the line
			}
			
		}
		
		//if destination has been selected. Start from the destination and build the shortest
		//path to the source going from the destination to it's predecessor and so on till you
		//reach the source
		
		else if (destination != null){
			Vertex<City> tempCity = mapToDraw.get(destination); //initialize place holder variables
			Vertex<City> target = destination; //start out at destination
			
			while(true){
				int x1 = target.getElement().getLocal().x; //get x and y coordinates
				int y1 = target.getElement().getLocal().y;

				
				if(target==source){ //if already at the source break out of the loop
					break;
				}
				
				tempCity = mapToDraw.get(target);
				
				
				target = tempCity;
				int x2 = tempCity.getElement().getLocal().x;
				int y2 = tempCity.getElement().getLocal().y;
				
				
				

				page2D.drawLine(x1, y1, x2, y2);

			}
		}	
    

		page2D.setStroke(oldStroke);    // restore the saved stroke
	}
	//updating the Jlabel according to different conditions
	
	if(source != null && destination != null && willUseDistance){ //if we have a source and a destination
		infoLabel.setText("Shortest path from " + source.getElement().getName()
				+ " to " + destination.getElement().getName() + " using distance"); //and using distance
	}
	else if(source != null && destination != null && !willUseDistance){ //if we have a source and destination
		infoLabel.setText("Shortest path from " + source.getElement().getName()
				+ " to " + destination.getElement().getName() + " using time"); //using time
	}
	else if(source != null && destination == null && willUseDistance){//similarly for having source but no
		infoLabel.setText("Shortest path from " + source.getElement().getName() //destination
				+ " to all it's neighbors using distance");
	}
	else if(source != null && destination == null && !willUseDistance){
		infoLabel.setText("Shortest path from " + source.getElement().getName()
				+ " to all it's neighbors using time");
	}
	
  }



  // Enable all rendering hints to enhance the quality.

  public static void setRenderingHints(Graphics2D page) {

    page.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,

        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    page.setRenderingHint(RenderingHints.KEY_ANTIALIASING,

        RenderingHints.VALUE_ANTIALIAS_ON);

    page.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,

        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    page.setRenderingHint(RenderingHints.KEY_INTERPOLATION,

        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

  }



  // Methods required by the MouseListener interface.

  

  // When the mouse is clicked, find which vertex it's over.

  // If it's over a vertex and we're finding the source,

  // record the source, clear the destination, enable the destination

  // button, and find and draw the shortest paths from the source.

  // If it's over a vertex and we're finding the destination, record

  // the destination, and find and draw a shortest path from the source

  // to the destination.

  public void mouseClicked(MouseEvent e) {
	  Point usable = e.getPoint(); //save the point
	  Vertex<City> current = roadmap.cityAt(usable); //find the closest city
	  
	  
	  if(current!=null && findingsource){ //don't think you need this but wait awhile
		  source = current; //if we are looking for a source set the current city as source
		  destination = null; //reset destination to null
		  destButton.setEnabled(true); //enable the destination finding button
		  mapToDraw =  roadmap.shortestPathLength(willUseDistance,source); //get the shortest distance map
		  repaint();
		  
	  }
	  else{
		  destination = current;
		  
		  //if(source == null){
			  //System.out.println(current);
		  //}
		  
		  repaint();
	  }
	  
  }


  public void mousePressed(MouseEvent e) { }

  public void mouseReleased(MouseEvent e) { }

  public void mouseEntered(MouseEvent e) { }

  public void mouseExited(MouseEvent e) { }



  // Return the preferred size of this component.

  @Override

  public Dimension getPreferredSize() {

    if (missingPicture)

      return new Dimension(320, 480);

    else

      return super.getPreferredSize();

  }


  // Needs to be here.

  @Override

  public Dimension getPreferredScrollableViewportSize() {

    return getPreferredSize();

  }



  // Needs to be here.

  @Override

  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation,

      int direction) {

    // Get the current position.

    int currentPosition = 0;

    if (orientation == SwingConstants.HORIZONTAL)

      currentPosition = visibleRect.x;

    else

      currentPosition = visibleRect.y;


    // Return the number of pixels between currentPosition

    // and the nearest tick mark in the indicated direction.

    if (direction < 0) {

      int newPosition = currentPosition - (currentPosition / maxUnitIncrement)

          * maxUnitIncrement;

      return (newPosition == 0) ? maxUnitIncrement : newPosition;

    }

    else

      return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement

          - currentPosition;

  }



  // Needs to be here.

  @Override

  public int getScrollableBlockIncrement(Rectangle visibleRect,

      int orientation, int direction) {

    if (orientation == SwingConstants.HORIZONTAL)

      return visibleRect.width - maxUnitIncrement;

    else

      return visibleRect.height - maxUnitIncrement;

  }



  // Needs to be here.

  @Override

  public boolean getScrollableTracksViewportWidth() {

    return false;

  }



  // Needs to be here.

  @Override

  public boolean getScrollableTracksViewportHeight() {

    return false;

  }


  // Needs to be here.

  public void setMaxUnitIncrement(int pixels) {

    maxUnitIncrement = pixels;

  }

  // Called when the source button is pressed.

  public void findSource() {

	  findingsource = true;
		  
  }



  // Called when the destination button is pressed.

  public void findDest() {

    // YOU FILL THIS IN.
	  destination = null;
	  findingsource = false;

  }



  // Called when the time button is pressed.  Tells the roadmap to use time

  // for edge weights, and finds and draws shortest paths.

  public void useTime() {
	  willUseDistance = false; //indicate we are using time 
	  mapToDraw =  roadmap.shortestPathLength(willUseDistance,source); //recalculate the shortest routes map
	  
	  /**
	  if(source != null && destination != null){
			infoLabel.setText("Shortest path from " + source.getElement().getName()
					+ " to " + destination.getElement().getName() + "using time");
	  }
	  else if(source != null && destination == null){
			infoLabel.setText("Shortest path from " + source.getElement().getName()
					+ " to " + "all it's neighbors using time");
	  }
	  */
	  
	  repaint();

  }



  // Called when the distance button is pressed.  Tells the roadmap to use distance

  // for edge weights, and finds and draws shortest paths.

  public void useDistance() {

	  willUseDistance = true; //indicate we are using distance
	  mapToDraw =  roadmap.shortestPathLength(willUseDistance,source); //calculate the shortest paths
	  
	  /**
	  if(source != null && destination != null){
			infoLabel.setText("Shortest path from " + source.getElement().getName()
					+ " to " + destination.getElement().getName() + "using distance");
	  }
	  else if(source != null && destination == null){
			infoLabel.setText("Shortest path from " + source.getElement().getName()
					+ " to " + "all it's neighbors using distance");
	  }
	  */
	  repaint();

  }


}