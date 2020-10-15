package telran.lostfound.domain.entities;

public class Location {
	
	public String country;
	public String city;
	public String street;
	public String building;
	public double longitude;
	public double latitude;
	
	public Location(String country, String city, String street, String building, double longitude, double latitude) {
		super();
		this.country = country;
		this.city = city;
		this.street = street;
		this.building = building;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Location() {
		super();
	}

}
