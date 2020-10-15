package telran.lostfound.domain.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import telran.lostfound.api.LostFoundDto;

@Document(collection = "lostfounds")
public class LostPetEntity {
	
	@Id
	String id;
	
	boolean typePost;
	String userLogin;
	String userName;
	String avatar;
	String datePost;
	
//	think about it
	String type;
	
	boolean sex;
	String breed;
	String[] tags;
	
//	API photos
	String[] photos;
	
	Location location;

	public LostPetEntity() {
		super();
	}

	public LostPetEntity(LostFoundDto dto, boolean typePost, String login) {
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
	
	

}
