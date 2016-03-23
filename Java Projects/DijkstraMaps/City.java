import java.awt.Point;

public class City{
	private String name;
	private Point local;
	private int radius;
	final int TOLERANCE_RADIUS = 5;
	
	public City(String name, Point location){
		this.name = name;
		this.local = location;
		this.radius = TOLERANCE_RADIUS;
	}
	
	public boolean isNear(Point p){
		return (local.distance(p) < radius);
		
	}

	public String getName() {
		return name;
	}
	
	public Point getLocal() {
		return local;
	}
}