package telran.lostfound.service.interfaces;

import java.net.URISyntaxException;
import java.util.ArrayList;

import telran.lostfound.api.ResponsePagesDto;
import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.RequestSearchDto;
import telran.lostfound.api.ResponseLostFoundDto;

public interface ILostFoundManagement {
	
//	new post 2
	ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) 
			throws URISyntaxException; 														//D get ret	checkedLoc
//	get post 2
	ResponsePagesDto getPostsOfLostFoundPets(int items, int currentPage, boolean typePost);	//D get ret	
//	search info 2
	ResponsePagesDto searchInfoOfLostOrFound(
			RequestSearchDto dto, int items, int currentPage, boolean typePost); 			//D get ret
	
	ResponsePostDto postById(String postID); 												//D get ret
	ResponsePostDto deletePostById(String postId);											//D get ret
	ResponsePostDto updatePost(RequestLostFoundDto dto, String postId);						//D get ret
	ArrayList<ResponsePostDto> getUserDataListId(String[] posts);							//D (Address->Location?)
	String[] getTagsAndcolorsOfPicture(String imageLink);									//D get ret	
	
	
}
