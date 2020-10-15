package telran.lostfound.api;

import telran.lostfound.domain.entities.Location;

public class PostDto {
	
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

}
