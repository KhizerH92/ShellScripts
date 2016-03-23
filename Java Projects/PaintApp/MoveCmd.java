import java.awt.Point;

public class MoveCmd extends Command{
	  Point old = null;
	  Shape s = null;
	  public void executeClick(Point p, Drawing dwg) {}
	  
	  public void executePress(Point p, Drawing dwg) {
		  s = dwg.getFrontmostContainer(p); //on the press takes the coordinate
		  if (s != null){
			  old = p;
		  }
		  else{
			  old = null;
		  }
	  }
	  
	  public void executeDrag(Point p, Drawing dwg) { //then moves it by how much the 
		  if (old!=null){ 				//mouse has moved since press
			  s.move(p.x-old.x, p.y-old.y);
			  old = p;
		  }
		  
	  }
  }