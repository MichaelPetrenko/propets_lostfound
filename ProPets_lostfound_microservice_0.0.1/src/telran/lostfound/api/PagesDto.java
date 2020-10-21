package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;

public class PagesDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public ResponseLostFoundDto[] posts;
	
	public PagesDto() {}

	public PagesDto(int itemsOnPage, int currentPage, int itemsTotal, ResponseLostFoundDto[] posts) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = posts;
	}
	
	
}
