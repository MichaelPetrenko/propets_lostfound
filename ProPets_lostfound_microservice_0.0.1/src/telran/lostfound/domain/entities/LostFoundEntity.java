package telran.lostfound.domain.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import telran.lostfound.api.RequestLostFoundDto;

@Document(collection = "lostfounds")
public class LostFoundEntity {
	
	@Id
	private String id;
	private boolean typePost;
	private String userLogin;
	private String userName;
	private String avatar;
	private String datePost;
//	think about it
	private String type;
	private String sex;
	private String breed;
	private String[] tags;
//	API photos
	private String[] photos;
	private Location location;

	public LostFoundEntity() {
		super();
	}

	public LostFoundEntity(RequestLostFoundDto dto, boolean typePost, String login) {
		super();
		this.typePost = typePost;
		this.userLogin = login;
		this.userName = dto.userName;
		this.avatar = dto.avatar;
		this.datePost = Instant.now().toString();
		this.type = dto.type;
		this.sex = dto.sex;
		this.breed = dto.breed;
		this.tags = dto.tags;
		this.photos = dto.photos;
		this.location = dto.location;
	}

	public String getId() {
		return id;
	}

	public boolean getTypePost() {
		return typePost;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public String getUserName() {
		return userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getDatePost() {
		return datePost;
	}

	public String getType() {
		return type;
	}

	public String getSex() {
		return sex;
	}

	public String getBreed() {
		return breed;
	}

	public String[] getTags() {
		return tags;
	}

	public String[] getPhotos() {
		return photos;
	}

	public Location getLocation() {
		return location;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTypePost(boolean typePost) {
		this.typePost = typePost;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setDatePost(String datePost) {
		this.datePost = datePost;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public void setPhotos(String[] photos) {
		this.photos = photos;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}