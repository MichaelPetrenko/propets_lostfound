package telran.lostfound.service.interfaces;

import java.net.URISyntaxException;
import java.util.ArrayList;

import telran.lostfound.api.ResponsePagesDto;
import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.RequestSearchDto;
import telran.lostfound.api.ResponseLostFoundDto;

public interface ILostFoundManagement {
	
	//Needs basic filter
	ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) 
			throws URISyntaxException; 														//DT get ret
	ResponsePagesDto getPostsOfLostFoundPets(int items, int currentPage, boolean typePost);	//DT get ret
	ResponsePagesDto searchInfoOfLostOrFound(
			RequestSearchDto dto, int items, int currentPage, boolean typePost); 			//DT get ret
	ResponsePostDto postById(String postID); 												//DT get ret tested
	
	//Need another filter - checking author and roles
	ResponsePostDto deletePostById(String postId);											//!!!!!!D get ret tested
	ResponsePostDto updatePost(RequestLostFoundDto dto, String postId);						//!!!!!!D get ret tested
	
	//Dont need filter
	ArrayList<ResponsePostDto> getUserDataListId(String[] posts);							//D tested (Address->Location?)
	String[] getTagsAndcolorsOfPicture(String imageLink);									//DT
	
}
