package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class RequestLostFoundDto {

	public String userName;
	public String avatar;
	public String type;
	public String sex;
	public String breed;
//	TODO if A->L, check A.
	public Address address;
	public LocationDto location;
	public String[] photos;
	public String[] tags;

	//Full constructor
	public RequestLostFoundDto(String userName, String avatar, String type, String sex, String breed, Address address, LocationDto location,
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

	//For update post method
	public RequestLostFoundDto(String type, String sex, String breed, LocationDto location, String[] photos,
			String[] tags) {
		super();
		this.type = type;
		this.sex = sex;
		this.breed = breed;
		this.location = location;
		this.photos = photos;
		this.tags = tags;
	}

}
