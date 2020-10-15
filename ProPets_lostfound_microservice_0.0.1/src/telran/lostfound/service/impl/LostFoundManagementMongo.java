package telran.lostfound.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponseGetPostsDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.codes.NoContentException;
import telran.lostfound.api.codes.NotExistsException;
import telran.lostfound.dao.LostfoundRepository;
import telran.lostfound.domain.entities.LostFoundEntity;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class LostFoundManagementMongo implements ILostFoundManagement {

	@Autowired
	LostfoundRepository repo;
	
	@Override
	public ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) {
		if(dto==null) {
			throw new NoContentException();
		}
		LostFoundEntity entity = new LostFoundEntity(dto, lostOrFound, login);
		repo.save(entity);
		
		ResponseLostFoundDto resp = new ResponseLostFoundDto(
				entity.getId(), entity.getTypePost(), entity.getUserLogin(), 
				entity.getUserName(), entity.getAvatar(), entity.getDatePost(), 
				entity.getType(), entity.getSex(), entity.getBreed(), 
				entity.getTags(), entity.getPhotos(), entity.getLocation());
			return resp;
	}

	@Override
	public ResponseGetPostsDto getPostsOfLostPets(int items, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGetPostsDto getPostsOfFoundPets(int items, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGetPostsDto searchInfoOfFound(int items, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGetPostsDto searchInfoOfLost(int items, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsePostDto postById(String postID) {
		if(postID==null) {
			throw new NotExistsException();
		}
		LostFoundEntity entity = repo.findById(postID).orElse(null);
		if(entity==null) {
			throw new NotExistsException();
		}
		
		ResponsePostDto resp = new ResponsePostDto(entity);
		return resp;
	}

	@Override
	public ResponseLostFoundDto newFoundPet(RequestLostFoundDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsePostDto deletePostById(String postId) {
		ResponsePostDto resp = postById(postId);
		repo.deleteById(resp.id);
		return resp;
	}

}
