package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class ResponseGetPostsDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public ResponsePostDto[] posts;
	public String[] photos;
	public Address address;
	public LocationDto location;
	
	public ResponseGetPostsDto(int itemsOnPage, int currentPage, int itemsTotal, ResponsePostDto[] posts, String[] photos,
			Address address, LocationDto location) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = posts;
		this.photos = photos;
		this.address = address;
		this.location = location;
	}

	public ResponseGetPostsDto() {
		super();
	}
	
}
