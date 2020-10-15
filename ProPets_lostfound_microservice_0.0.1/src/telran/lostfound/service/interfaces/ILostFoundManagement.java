package telran.lostfound.service.interfaces;

import telran.lostfound.api.LostFoundDto;
import telran.lostfound.api.PostDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.GetPostsResponseDto;

public interface ILostFoundManagement {
	
	ResponseLostFoundDto newLostPet(LostFoundDto dto); //get ret x-token
	ResponseLostFoundDto newFoundPet(LostFoundDto dto); //get ret
	GetPostsResponseDto getPostsOfLostPets(int items, int currentPage); // get ret
	GetPostsResponseDto getPostsOfFoundPets(int items, int currentPage);  //get ret
	
	GetPostsResponseDto searchInfoOfFound( //get ret
//			dto from Imaga,
			int items, int currentPage);
	GetPostsResponseDto searchInfoOfLost( //get ret
//			dto from Imaga,
			int items, int currentPage);
	
	PostDto postById(String postID); //ret get
	
	//update post get ret
	//delete post by id get ret
	
	//tags and colors CONNECT TO API 
	
	//get user data ??? from another service: input _ID_ posts, out content posts by ID.
			
}
