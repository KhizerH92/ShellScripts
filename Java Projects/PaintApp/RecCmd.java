import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.awt.event.ActionEvent;

public class RecCmd extends Command{
	  Point oneCorner = null;
	  Rect r;
	  
	  
	  @SuppressWarnings("unused")
	  public void executeClick(ActionEvent event){		  
	  }
	  public void executePress(Point e, Drawing dwg){
		oneCorner = e;
		r = new Rect(oneCorner.x,oneCorner.y, 0,0, dwg.getColor());
		dwg.add(r);
	  }
	  public void executeDrag(Point g, Drawing dwg){
		  if (r != null) { // make sure that currentRect exists
			  Point pt = g;
			  r.setX(Math.min(pt.x, oneCorner.x));
			  r.setY(Math.min(pt.y, oneCorner.y));
			  r.setWidth(Math.abs(pt.x - oneCorner.x));
			  r.setHeight(Math.abs(pt.y - oneCorner.y));
			  }
	  }
	  
  }