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
			throws URISyntaxException; 														//D get ret	tested unfinal
//	get post 2
	ResponsePagesDto getPostsOfLostFoundPets(int items, int currentPage, boolean typePost);	//D get ret	tested
//	search info 2
	ResponsePagesDto searchInfoOfLostOrFound(
			RequestSearchDto dto, int items, int currentPage, boolean typePost); 			//D get ret tested
	
	ResponsePostDto postById(String postID); 												//D get ret tested
	ResponsePostDto deletePostById(String postId);											//D get ret tested
	ResponsePostDto updatePost(RequestLostFoundDto dto, String postId);						//D get ret tested
	ArrayList<ResponsePostDto> getUserDataListId(String[] posts);							//D tested (Address->Location?)
	String[] getTagsAndcolorsOfPicture(String imageLink);									//D get ret	tested
	
}
