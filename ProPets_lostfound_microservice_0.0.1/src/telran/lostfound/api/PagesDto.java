package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class PagesDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public ResponseLostFoundDto[] posts;
	public String[] photos;
	public Address address;
	public Location location;
	
	public PagesDto() {}

	public PagesDto(int itemsOnPage, int currentPage, int itemsTotal, ResponseLostFoundDto[] posts, String[] photos,
			Address address, Location location) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = posts;
		this.photos = photos;
		this.address = address;
		this.location = location;
	}
	
	
}
