package telran.lostfound.api;

public class GeolocationPointMongoDto {
	String type;//: "Point",
    double[] coordinates;//: [-73.856077, 40.848447]
	
    public GeolocationPointMongoDto(double longitude, double latitude) {
		super();
		this.type = "Point";
		double[] coord = new double[2];
		coord[0] = longitude;
		coord[1] = latitude;
		this.coordinates = coord;
	}

	public String getType() {
		return type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
