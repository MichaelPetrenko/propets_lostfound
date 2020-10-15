package telran.lostfound.api;

import telran.lostfound.domain.entities.Location;

public class GetPostsResponseDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public PostDto[] posts;
	public String[] photos;
	public Location location;
	
	public GetPostsResponseDto(int itemsOnPage, int currentPage, int itemsTotal, PostDto[] posts, String[] photos,
			Location location) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = posts;
		this.photos = photos;
		this.location = location;
	}

	public GetPostsResponseDto() {
		super();
	}
	
}
