package telran.lostfound.controllers;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.lostfound.api.LostFoundApiConstants;
import telran.lostfound.api.PagesDto;
import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.dao.LostfoundRepository;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@RestController
public class LostfoundController {

	@Autowired
	ILostFoundManagement lostfound;
	
	@Autowired
	LostfoundRepository repo;
	
//	"/lostfound/en/v1/lost/{login}"
	@PostMapping(value = LostFoundApiConstants.NEW_LOST_PET)
	ResponseLostFoundDto newLostPet(@RequestBody RequestLostFoundDto lostFoundDto, @PathVariable String login) throws URISyntaxException { //TODO FIXME
		return lostfound.newLostOrFoundPet(lostFoundDto, login, false);
	}
	
//	"/lostfound/en/v1/found/{login}"
	@PostMapping(value = LostFoundApiConstants.NEW_FOUND_PET)
	ResponseLostFoundDto newFoundPet(@RequestBody RequestLostFoundDto lostFoundDto, @PathVariable String login) throws URISyntaxException { //TODO FIXME
		return lostfound.newLostOrFoundPet(lostFoundDto, login, true);
	}
	
//	"/lostfound/en/v1/losts"
	@GetMapping(value = LostFoundApiConstants.GET_POSTS_OF_LOST_PETS)
	PagesDto getPostsOfLostPets(@RequestParam int currentPage, @RequestParam int itemsOnPage) {
		return lostfound.getPostsOfLostFoundPets(itemsOnPage, currentPage, false);
	}
	
//	"/lostfound/en/v1/founds"
	@GetMapping(value = LostFoundApiConstants.GET_POSTS_OF_FOUND_PETS)
	PagesDto getPostsOfFoundPets(@RequestParam int currentPage, @RequestParam int itemsOnPage) {
		return lostfound.getPostsOfLostFoundPets(itemsOnPage, currentPage, true);
	}
	
//	"/lostfound/en/v1/{id}"
	@GetMapping(value = LostFoundApiConstants.POST_BY_ID)
	ResponsePostDto postById(@PathVariable("id") String id) {
		return lostfound.postById(id);
	}
	
//	"/lostfound/en/v1/{id}"
	@DeleteMapping(value = LostFoundApiConstants.DELETE_POST_BY_ID)
	ResponsePostDto deletePostById(@PathVariable("id") String id) {
		return lostfound.deletePostById(id);
	}
	
//	"/lostfound/en/v1/{id}"
	@PutMapping(value = LostFoundApiConstants.UPDATE_POST_FOUND_LOST_PET)
	ResponsePostDto updatePost(@RequestBody RequestLostFoundDto dto, @PathVariable("id") String id) {
		return lostfound.updatePost(dto, id);
	}
	
//	"/lostfound/en/v1/userdata"
	@PostMapping(value = LostFoundApiConstants.GET_USER_DATA_LIST_ID)
	ArrayList<ResponsePostDto> getUserDataListId(@RequestBody String[] posts) {
		return lostfound.getUserDataListId(posts);
	}
	
//	"/lostfound/en/v1/tagscolors/"
	@GetMapping(value = LostFoundApiConstants.TAGS_AND_COLORS_OF_PICTURE)
	String[] getTagsAndcolorsOfPicture(@RequestParam String image_url) {
		return lostfound.getTagsAndcolorsOfPicture(image_url);
	}
	
}
