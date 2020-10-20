package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class ResponseLostFoundDto {
	public String id;
	public boolean typePost;
	public String userLogin;
	public String userName;
	public String avatar;
	public String datePost;
	public String type;
	public String sex;
	public String breed;
	public String[] tags;
	public String[] photos;
	public Address address;
	public Location location;
	
	public ResponseLostFoundDto() {}

	public ResponseLostFoundDto(String id, boolean typePost, String userLogin, String userName, String avatar,
			String datePost, String type, String sex, String breed, String[] tags, String[] photos,
			Address address, Location location) {
		super();
		this.id = id;
		this.typePost = typePost;
		this.userLogin = userLogin;
		this.userName = userName;
		this.avatar = avatar;
		this.datePost = datePost;
		this.type = type;
		this.sex = sex;
		this.breed = breed;
		this.tags = tags;
		this.photos = photos;
		this.address = address;
		this.location = location;
	}
	
	
}
