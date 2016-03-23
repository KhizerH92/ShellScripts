import java.awt.Point;

public class ColorCmd extends Command{
	  public void executeClick(Point p, Drawing dwg) { 
		  Shape s = dwg.getFrontmostContainer(p);
		  if(s!=null){
		  s.setColor(dwg.getColor()); //changes the color of the front most
		  }						//object to the new color chosen
	  }	

	  public void executePress(Point p, Drawing dwg) { }
	  
	  public void executeDrag(Point p, Drawing dwg) { }
  }
