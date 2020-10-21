package telran.lostfound.api;

import telran.lostfound.domain.entities.Address;
import telran.lostfound.domain.entities.LostFoundEntity;

public class PagesDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public ResponseLostFoundDto[] posts;
	
	public PagesDto() {}

	public PagesDto(int itemsOnPage, int currentPage, int itemsTotal, LostFoundEntity[] posts) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = new ResponseLostFoundDto[posts.length];
		for(int i =0 ; i<posts.length; i++) {
			this.posts[i] = new ResponseLostFoundDto(
					posts[i].getId(), posts[i].getTypePost(), posts[i].getUserLogin(), 
					posts[i].getUserName(), posts[i].getAvatar(), posts[i].getDatePost(), 
					posts[i].getType(), posts[i].getSex(), posts[i].getBreed(), 
					posts[i].getTags(), posts[i].getPhotos(), posts[i].getAddress(), 
					new Location(posts[i].getLocation()[0], posts[i].getLocation()[1]));
		}
	}
	
	
}
