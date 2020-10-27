package telran.lostfound.api;

import java.util.ArrayList;
import java.util.List;

import telran.lostfound.domain.entities.LostFoundEntity;

public class PagesDto {

	public int itemsOnPage;
	public int currentPage;
	public int itemsTotal;
	public List<ResponseLostFoundDto> posts;

	public PagesDto() {
	}

	public PagesDto(int itemsOnPage, int currentPage, int itemsTotal, List<LostFoundEntity> list) {
		super();
		this.itemsOnPage = itemsOnPage;
		this.currentPage = currentPage;
		this.itemsTotal = itemsTotal;
		this.posts = new ArrayList<ResponseLostFoundDto>();

		for (LostFoundEntity ent : list) {
			this.posts.add(
					new ResponseLostFoundDto
					(ent.getId(), ent.getTypePost(), ent.getUserLogin(),
					ent.getUserName(), ent.getAvatar(), ent.getDatePost(), 
					ent.getType(), ent.getSex(), ent.getBreed(),
					ent.getTags(), ent.getPhotos(), ent.getAddress(),
					new Location(ent.getLocation()[0], ent.getLocation()[1])));

		}
	}

}
