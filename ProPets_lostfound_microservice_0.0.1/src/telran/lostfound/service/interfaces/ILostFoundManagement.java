package telran.lostfound.service.interfaces;

import java.util.ArrayList;

import telran.lostfound.api.LostFoundDto;
import telran.lostfound.api.PostDto;
import telran.lostfound.api.ResponseLostFoundDto;

public interface ILostFoundManagement {
	
	ResponseLostFoundDto newLostPet(LostFoundDto dto);
	ResponseLostFoundDto newFoundPet(LostFoundDto dto);
	ArrayList<PostDto> getPostsOfLostPets(int items, int currentPage); //fix it later
	

}
