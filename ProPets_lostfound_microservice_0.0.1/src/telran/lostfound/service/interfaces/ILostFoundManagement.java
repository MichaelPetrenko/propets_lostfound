package telran.lostfound.service.interfaces;

import telran.lostfound.api.LostFoundDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.GetPostsResponseDto;

public interface ILostFoundManagement {
	
	ResponseLostFoundDto newLostPet(LostFoundDto dto);
	ResponseLostFoundDto newFoundPet(LostFoundDto dto);
	GetPostsResponseDto getPostsOfLostPets(int items, int currentPage); //fix it later
	

}
