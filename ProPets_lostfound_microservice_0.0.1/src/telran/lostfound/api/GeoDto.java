//package telran.lostfound.api;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
//
//public class GeoDto {
//
//	@Id
//	private String id;
//	@GeoSpatialIndexed(name = "Location")
//	private double[] position;
//
//	public GeoDto(double longtitude, double latitude) {
//		double[] position = new double[2];
//		position[0] = longtitude;
//		position[1] = latitude;
//		this.position = position;
//	}
//
//	public GeoDto() {
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public double[] getPosition() {
//		return position;
//	}
//
//	public void setPosition(double[] position) {
//		this.position = position;
//	}
//
//}
