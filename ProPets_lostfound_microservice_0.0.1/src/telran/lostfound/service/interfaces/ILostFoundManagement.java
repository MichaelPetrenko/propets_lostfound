package telran.lostfound.service.interfaces;

import java.net.URISyntaxException;
import java.util.ArrayList;

import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponseGetPostsDto;

public interface ILostFoundManagement {
	
	ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) throws URISyntaxException; //D get ret x-token
	
	ResponseGetPostsDto getPostsOfLostPets(int items, int currentPage);									//get ret
	ResponseGetPostsDto getPostsOfFoundPets(int items, int currentPage);								//get ret
	
	ResponsePostDto postById(String postID); 															//D get ret
	ResponsePostDto deletePostById(String postId);														//D get ret
	ResponsePostDto updatePost(RequestLostFoundDto dto, String postId);									//D get ret
	
	ArrayList<ResponsePostDto> getUserDataListId(String[] posts);										//TODO FIXME TODO Location!!!!!!!!!!!!!!!!	
	String[] getTagsAndcolorsOfPicture(String imageLink);
	ResponseGetPostsDto searchInfoOfFound(int items, int currentPage);
	ResponseGetPostsDto searchInfoOfLost(int items, int currentPage);
	
}
