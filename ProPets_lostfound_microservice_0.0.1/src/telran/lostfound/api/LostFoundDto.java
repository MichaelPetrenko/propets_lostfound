package telran.lostfound.api;

import telran.lostfound.domain.entities.Location;

public class LostFoundDto {

	public String userName;
	public String avatar;
	public String type;
	public boolean sex;
	public String breed;
	public Location location;
	public String[] photos;
	public String[] tags;

	public LostFoundDto(String userName, String avatar, String type, boolean sex, String breed, Location location,
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

	public LostFoundDto() {
		super();
	}

}
