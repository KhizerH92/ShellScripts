public class Highway{ //creating our own edge class kind of
	private double travelDist;
	private double travelTime;
	
	public Highway(double dist, double time){
		this.setTravelDist(dist);
		this.setTravelTime(time);
	}

	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public double getTravelDist() {
		return travelDist;
	}

	public void setTravelDist(double travelDist) {
		this.travelDist = travelDist;
	}
}