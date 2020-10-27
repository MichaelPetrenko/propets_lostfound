package telran.lostfound.api;

public class LocationDto {
	
	public double longitude;
	public double latitude;
	
	public LocationDto(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public LocationDto() {}
}
