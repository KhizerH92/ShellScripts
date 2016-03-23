import java.awt.Point;
import java.awt.event.ActionEvent;

public class SegCmd extends Command{
	  Point one = null;
	  Point pt = null;
	  Segment r;
	  
	  @SuppressWarnings("unused")
	  public void executeClick(ActionEvent event){		  
	  }
	  public void executePress(Point e, Drawing dwg){
		one = e;
		r = new Segment(one.x,one.y, one.x,one.y, dwg.getColor());
		dwg.add(r); //changes made here
	  }
	  public void executeDrag(Point g, Drawing dwg) {
		  if (r != null) {  //
			  pt = g;
			  r = new Segment(one.x,one.y, pt.x,pt.y, dwg.getColor());
			  dwg.replaceFront(r); //use replacefront here
		  }
	  }
  }