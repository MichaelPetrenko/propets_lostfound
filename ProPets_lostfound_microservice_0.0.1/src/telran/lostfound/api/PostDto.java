package telran.lostfound.api;

import telran.lostfound.domain.entities.Location;
import telran.lostfound.domain.entities.LostFoundEntity;

public class PostDto {
	
	public String id;
	public boolean typePost;
	public String userLogin;
	public String userName;
	public String avatar;
	public String datePost;	
	public String type;
	public boolean sex;
	public String breed;
	public String[] tags;
	public String[] photos;
	public Location location;
	
	public PostDto(LostFoundEntity ent) {
		super();
		this.id = ent.getId();
		this.typePost = ent.getTypePost();
		this.userLogin = ent.getUserLogin();
		this.userName = ent.getUserName();
		this.avatar = ent.getAvatar();
		this.datePost = ent.getDatePost();
		this.type = ent.getType();
		this.sex = ent.getSex();
		this.breed = ent.getBreed();
		this.tags = ent.getTags();
		this.photos = ent.getPhotos();
		this.location = ent.getLocation();
	}
	
	public PostDto() {
		super();
	}
	
	

}
