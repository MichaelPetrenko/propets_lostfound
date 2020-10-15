package telran.lostfound.service.interfaces;

import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponseGetPostsDto;

public interface ILostFoundManagement {
	
	ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound); //get ret x-token
	ResponseLostFoundDto newFoundPet(RequestLostFoundDto dto); 											//get ret
	ResponseGetPostsDto getPostsOfLostPets(int items, int currentPage);									// get ret
	ResponseGetPostsDto getPostsOfFoundPets(int items, int currentPage);								//get ret
	ResponsePostDto postById(String postID); 															//get ret
	ResponsePostDto deletePostById(String postId);														//get ret
	
	//=============================================================================
	
	ResponseGetPostsDto searchInfoOfFound( //get ret
//			dto from Imaga,
			int items, int currentPage);
	ResponseGetPostsDto searchInfoOfLost( //get ret
//			dto from Imaga,
			int items, int currentPage);
	
	
	
	//update post get ret

	
	//tags and colors CONNECT TO API 
	
	//get user data ??? from another service: input _ID_ posts, out content posts by ID.
			
}
