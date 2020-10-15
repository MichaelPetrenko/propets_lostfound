package telran.lostfound.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.lostfound.api.ResponseGetPostsDto;
import telran.lostfound.api.LostFoundApiConstants;
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
	ResponseLostFoundDto newLostPet(@RequestBody RequestLostFoundDto lostFoundDto, @PathVariable String login) {
		return lostfound.newLostOrFoundPet(lostFoundDto, login, false);
	}
	
//	"/lostfound/en/v1/found/{login}"
	@PostMapping(value = LostFoundApiConstants.NEW_FOUND_PET)
	ResponseLostFoundDto newFoundPet(@RequestBody RequestLostFoundDto lostFoundDto, @PathVariable String login) {
		return lostfound.newLostOrFoundPet(lostFoundDto, login, true);
	}
	
//	"/lostfound/en/v1/losts"
	@GetMapping(value = LostFoundApiConstants.GET_POSTS_OF_LOST_PETS)
	ResponseGetPostsDto getPostsOfLostPets(@RequestParam int currentPage, @RequestParam int itemsOnPage) {
		return lostfound.getPostsOfLostPets(itemsOnPage, currentPage);
	}
	
//	"/lostfound/en/v1/founds"
	@GetMapping(value = LostFoundApiConstants.GET_POSTS_OF_FOUND_PETS)
	ResponseGetPostsDto getPostsOfFoundPets(@RequestParam int currentPage, @RequestParam int itemsOnPage) {
		return lostfound.getPostsOfFoundPets(itemsOnPage, currentPage);
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
	
}