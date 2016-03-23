import java.awt.Point;

public class DeleteCmd extends Command{
	  public void executeClick(Point p, Drawing d){
		  Shape s = d.getFrontmostContainer(p);
		  if (s != null){
			  d.remove(s);
		  }
	  }
  }