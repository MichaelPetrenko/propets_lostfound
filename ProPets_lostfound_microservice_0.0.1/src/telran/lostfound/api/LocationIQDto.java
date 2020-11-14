package telran.lostfound.api;

public class LocationIQDto {
	
	public long place_id;
	public String licence;
	public String osm_type;
	public long osm_id;
	public double[] boundingbox;
	public double lat;
	public double lon;
	public String display_name;
//	public String class;
	public String type;
	public double improtance;
	
	public LocationIQDto() {
		super();
	}

	public LocationIQDto(long place_id, String licence, String osm_type, long osm_id, double[] boundingbox, double lat,
			double lon, String display_name, String type, double improtance) {
		super();
		this.place_id = place_id;
		this.licence = licence;
		this.osm_type = osm_type;
		this.osm_id = osm_id;
		this.boundingbox = boundingbox;
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
		this.type = type;
		this.improtance = improtance;
	}
	
}
