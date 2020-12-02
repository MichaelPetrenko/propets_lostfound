package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class LostFoundKafkaDto {
	
	public String id;
	public KafkaReqType kafkaReqType;
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
	public LocationDto location;
	
	public LostFoundKafkaDto() {}

	public LostFoundKafkaDto(String id, KafkaReqType kafkaReqType, boolean typePost, String userLogin, String userName,
			String avatar, String datePost, String type, String sex, String breed, String[] tags, String[] photos,
			Address address, LocationDto location) {
		super();
		this.id = id;
		this.kafkaReqType = kafkaReqType;
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

	public LostFoundKafkaDto(ResponseLostFoundDto dto, KafkaReqType kafkaReqType) {
		this.kafkaReqType = kafkaReqType;
		this.id = dto.id;
		this.typePost = dto.typePost;
		this.userLogin = dto.userLogin;
		this.userName = dto.userName;
		this.avatar = dto.avatar;
		this.datePost = dto.datePost;
		this.type = dto.type;
		this.sex = dto.sex;
		this.breed = dto.breed;
		this.tags = dto.tags;
		this.photos = dto.photos;
		this.address = dto.address;
		this.location = dto.location;
	}

}
