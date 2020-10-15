package telran.lostfound.api;

import telran.lostfound.domain.entities.Location;

public class RequestLostFoundDto {

	public String userName;
	public String avatar;
	public String type;
	public String sex;
	public String breed;
	public Location location;
	public String[] photos;
	public String[] tags;

	public RequestLostFoundDto(String userName, String avatar, String type, String sex, String breed, Location location,
			String[] photos, String[] tags) {
		super();
		this.userName = userName;
		this.avatar = avatar;
		this.type = type;
		this.sex = sex;
		this.breed = breed;
		this.location = location;
		this.photos = photos;
		this.tags = tags;
	}

	public RequestLostFoundDto() {
		super();
	}

}