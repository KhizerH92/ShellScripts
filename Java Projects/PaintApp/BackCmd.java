import java.awt.Point;

public class BackCmd extends Command{
	  
	  public void executeClick(Point p, Drawing dwg) {
		  Shape s = dwg.getFrontmostContainer(p);
		  if ( s != null){
			dwg.moveToBack(s);  
		  }
	  }
	  
	  public void executePress(Point p, Drawing dwg) { }
	  
	  public void executeDrag(Point p, Drawing dwg) { }
  }
  