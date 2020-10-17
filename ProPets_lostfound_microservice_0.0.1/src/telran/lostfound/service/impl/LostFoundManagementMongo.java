package telran.lostfound.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponseGetPostsDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.codes.NoContentException;
import telran.lostfound.api.codes.NotExistsException;
import telran.lostfound.api.imaga.Color;
import telran.lostfound.api.imaga.ColorsApiResult;
import telran.lostfound.api.imaga.ColorsResult;
import telran.lostfound.api.imaga.Tag;
import telran.lostfound.api.imaga.Tags;
import telran.lostfound.api.imaga.TagsApiResult;
import telran.lostfound.dao.LostfoundRepository;
import telran.lostfound.domain.entities.LostFoundEntity;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class LostFoundManagementMongo implements ILostFoundManagement {

	@Autowired
	LostfoundRepository repo;

	@Override
	public ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) throws URISyntaxException {
		if (dto == null) {
			throw new NoContentException();
		}
		LostFoundEntity entity = new LostFoundEntity(dto, lostOrFound, login);
		System.out.println(" - - - - - - - - -- " + entity.getPhotos().toString());
		System.out.println(" - - - - -- - - - -- "+ entity.getPhotos()[0].toString());
		entity.setTags(getTagsFromImagga(entity.getPhotos()[0]));
		repo.save(entity);

		ResponseLostFoundDto resp = new ResponseLostFoundDto(entity.getId(), entity.getTypePost(),
				entity.getUserLogin(), entity.getUserName(), entity.getAvatar(), entity.getDatePost(), entity.getType(),
				entity.getSex(), entity.getBreed(), entity.getTags(), entity.getPhotos(), entity.getLocation());
		return resp;
	}

	@Override
	public ResponsePostDto updatePost(RequestLostFoundDto dto, String postId) {
		if (dto == null || postId == null) {
			throw new NoContentException();
		}
		LostFoundEntity entity = repo.findById(postId).orElse(null);
		if (entity == null) {
			throw new NotExistsException();
		}
		entity.setType(dto.type);
		entity.setSex(dto.sex);
		entity.setBreed(dto.breed);
		entity.setLocation(dto.location);
		entity.setPhotos(dto.photos);
		entity.setTags(dto.tags);
		repo.save(entity);

		ResponsePostDto req = new ResponsePostDto(entity);
		return req;
	}

	@Override
	public ArrayList<ResponsePostDto> getUserDataListId(String[] posts) {
		ArrayList<ResponsePostDto> res = new ArrayList<>();
		for (String post : posts) {
			LostFoundEntity entity = repo.findById(post).orElse(null);
			if (entity != null) {
				res.add(new ResponsePostDto(entity));
			}
		}
		return res;
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
		if (postID == null) {
			throw new NotExistsException();
		}
		LostFoundEntity entity = repo.findById(postID).orElse(null);
		if (entity == null) {
			throw new NotExistsException();
		}

		ResponsePostDto resp = new ResponsePostDto(entity);
		return resp;
	}

	@Override
	public ResponsePostDto deletePostById(String postId) {
		ResponsePostDto resp = postById(postId);
		repo.deleteById(resp.id);
		return resp;
	}

	@Override
	public String[] getTagsFromImagga(String imageLink) throws URISyntaxException {

		String[] colors = checkingColorsFromI(imageLink);
		String[] tags = checkingTagsFromI(imageLink);
		String[] res = uniteArrays(tags, colors);
		
		return res;
	}

	private String[] uniteArrays(String[] tags, String[] colors) {
		String[] res = new	String[tags.length+colors.length];
		for (int i = 0; i < 3; i++) {
			res[i] = tags[i];
		}
		for (int i = 3, j = 0; j < 2; j++, i++) {
			res[i] = colors[j];
		}
		return res;
	}

	private String[] checkingTagsFromI(String imageLink) throws URISyntaxException {

		RestTemplate restTemplate = new RestTemplate();
		String endPointTags = "https://api.imagga.com/v2/tags?image_url=";
		RequestEntity<Void> request = RequestEntity.get(new URI(endPointTags + imageLink)).header("Authorization",
				"Basic YWNjX2EwZDljMDBhNGM0MTEzYjpiZWNlYWU1YTdmODE3NTNhNmEzMzM2OWQxNzc3MWMwYg==").build();
		ResponseEntity<TagsApiResult> response = restTemplate.exchange(request, TagsApiResult.class);

		if (!response.getBody().status.type.equals("success")) {
			return null;
		}

		Tags result = response.getBody().result;
		Tag[] tagsArr = result.tags;
		String[] finalRes = new String[3];

		for (int i = 0; i < 3; i++) {
			Tag tagIter = tagsArr[i];
			String str = tagIter.tag.get("en");
			finalRes[i] = str;
		}

		return finalRes;

	}

	private String[] checkingColorsFromI(String imageLink) throws URISyntaxException {

		RestTemplate restTemplate = new RestTemplate();
		String endPointColors = "https://api.imagga.com/v2/colors?image_url=";
		RequestEntity<Void> request = RequestEntity.get(new URI(endPointColors + imageLink)).header("Authorization",
				"Basic YWNjX2EwZDljMDBhNGM0MTEzYjpiZWNlYWU1YTdmODE3NTNhNmEzMzM2OWQxNzc3MWMwYg==").build();
		ResponseEntity<ColorsApiResult> response = restTemplate.exchange(request, ColorsApiResult.class);

		if (!response.getBody().status.type.equals("success")) {
			return null;
		}

		ColorsResult result = response.getBody().result;
		Color[] foreColorArr = result.colors.foreground_colors;
		String[] finalRes = new String[2];

		for (int i = 0; i < 2; i++) {
			Color color = foreColorArr[i];
			String str = color.closest_palette_color_parent;
			finalRes[i] = str;
		}

		return finalRes;

	}

}
