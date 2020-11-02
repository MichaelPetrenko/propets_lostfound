package telran.lostfound.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.lostfound.api.LocationDto;
import telran.lostfound.api.ResponsePagesDto;
import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.RequestSearchDto;
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

	private final String imaggaAuth = "Basic YWNjX2EwZDljMDBhNGM0MTEzYjpiZWNlYWU1YTdmODE3NTNhNmEzMzM2OWQxNzc3MWMwYg==";
	private final int contentTagsLength = 3;
	private final int colorTagsLength = 2;

	@Autowired
	LostfoundRepository repo;

	@Override
	public ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound) {
		if (dto == null) {
			throw new NoContentException();
		}
		
		// TODO Here we need to check Location - if not ex-s - to make it from address. LATER
		
		if(dto.breed==null || dto.location==null || dto.tags==null || dto.type==null) {
			throw new NoContentException("Wrond data!");
		}
		
		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location");
		};
		
		LostFoundEntity entity = new LostFoundEntity(dto, lostOrFound, login);
		repo.save(entity);

		double[] coord = entity.getLocation();

		ResponseLostFoundDto resp = new ResponseLostFoundDto(entity.getId(), entity.getTypePost(),
				entity.getUserLogin(), entity.getUserName(), entity.getAvatar(), entity.getDatePost(), entity.getType(),
				entity.getSex(), entity.getBreed(), entity.getTags(), entity.getPhotos(), entity.getAddress(),
				new LocationDto(coord[0], coord[1]));
		
		return resp;
	}

	@Override
	public ResponsePostDto updatePost(RequestLostFoundDto dto, String postId) {
		if (dto == null) {
			throw new NoContentException();
		}
		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location!");
		};
		LostFoundEntity entity = repo.findById(postId).orElse(null);
		if (entity == null) {
			throw new NotExistsException();
		}
		entity.setType(dto.type);
		entity.setSex(dto.sex);
		entity.setBreed(dto.breed);
		entity.setAddress(dto.address);

		double[] res = new double[2];
		res[0] = dto.location.longitude;
		res[1] = dto.location.latitude;
		entity.setLocation(res);
		entity.setPhotos(dto.photos);
		entity.setTags(dto.tags);
		repo.save(entity);

		ResponsePostDto req = new ResponsePostDto(entity);
		return req;
	}

	@Override
	public ArrayList<ResponsePostDto> getUserDataListId(String[] posts) {
		if(posts.length==0) {
			throw new NoContentException("posts is empty!");
		}
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
		if (postId == null) {
			throw new NotExistsException();
		}
		ResponsePostDto resp = postById(postId);
		if (resp == null) {
			throw new NotExistsException();
		}
		repo.deleteById(resp.id);
		return resp;
	}

	@Override
	public String[] getTagsAndcolorsOfPicture(String imageLink) {
		try {
			String[] colors = gettingColorsOfImgFromImagga(imageLink);
			String[] tags = gettingTagsOfImgFromImagga(imageLink);
			String[] res = uniteArrays(tags, colors);
			return res;
		} catch (Exception e) {
			throw new NoContentException("link on image is not correct");
		}
	}

	private String[] uniteArrays(String[] tags, String[] colors) {
		String[] res = new String[contentTagsLength + colorTagsLength];
		for (int i = 0; i < contentTagsLength; i++) {
			res[i] = tags[i];
		}
		for (int i = contentTagsLength, j = 0; j < colorTagsLength; j++, i++) {
			res[i] = colors[j];
		}
		return res;
	}

	private String[] gettingTagsOfImgFromImagga(String imageLink) {
		RestTemplate restTemplate = new RestTemplate();
		String endPointTags = "https://api.imagga.com/v2/tags?image_url=";
		URI uri;
		try {
			uri = new URI(endPointTags + imageLink);
		} catch (Exception e) {
			throw new NoContentException();
		}
		RequestEntity<Void> request = RequestEntity.get(uri).header("Authorization", imaggaAuth).build();
		ResponseEntity<TagsApiResult> response = restTemplate.exchange(request, TagsApiResult.class);

		if (!response.getBody().status.type.equals("success")) {
			return null;
		}

		Tags result = response.getBody().result;
		Tag[] tagsArr = result.tags;
		String[] finalRes = new String[contentTagsLength];

		for (int i = 0; i < contentTagsLength; i++) {
			Tag tagIter = tagsArr[i];
			String str = tagIter.tag.get("en");
			finalRes[i] = str;
		}
		return finalRes;
	}

	private String[] gettingColorsOfImgFromImagga(String imageLink) {
		RestTemplate restTemplate = new RestTemplate();
		String endPointColors = "https://api.imagga.com/v2/colors?image_url=";
		URI uri;
		try {
			uri = new URI(endPointColors + imageLink);
		} catch (Exception e) {
			throw new NoContentException();
		}
		RequestEntity<Void> request = RequestEntity.get(uri).header("Authorization", imaggaAuth).build();
		ResponseEntity<ColorsApiResult> response = restTemplate.exchange(request, ColorsApiResult.class);

		if (!response.getBody().status.type.equals("success")) {
			return null;
		}

		ColorsResult result = response.getBody().result;
		Color[] foreColorArr = result.colors.foreground_colors;
		String[] finalRes = new String[colorTagsLength];

		for (int i = 0; i < colorTagsLength; i++) {
			Color color = foreColorArr[i];
			String str = color.closest_palette_color_parent;
			finalRes[i] = str;
		}
		return finalRes;
	}

	@Override
	public ResponsePagesDto getPostsOfLostFoundPets(int items, int currentPage, boolean typePost) {
		
		Pageable pageable = PageRequest.of(currentPage, items);

		int itemsTotal = repo.findAllByTypePost(typePost).size(); 
		List<LostFoundEntity> postsList = repo.findAllByTypePost(typePost, pageable);
		
		ResponsePagesDto pDto = new ResponsePagesDto(items, currentPage, itemsTotal, postsList);
		return pDto;
	}

	@Override
	public ResponsePagesDto searchInfoOfLostOrFound(RequestSearchDto dto, int items, int currentPage, boolean typePost) {
		
		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location!");
		};
		
		Distance radiusOfSearch = new Distance(10, Metrics.KILOMETERS);
		Point pointOfSearch = new Point(dto.location.longitude, dto.location.latitude);
		Pageable pageable = PageRequest.of(currentPage, items);

		List<LostFoundEntity> allFounds = repo.findByLocationNearAndTypePostAndType(pointOfSearch, radiusOfSearch, typePost, dto.type, pageable);
		int itemsTotal = repo.findByLocationNearAndTypePostAndType(pointOfSearch, radiusOfSearch, typePost, dto.type).size();
		ResponsePagesDto pDto = new ResponsePagesDto(items, currentPage, itemsTotal, allFounds);
		return pDto;
	}
	
	private boolean checkCorrectDataLocation(LocationDto location) {
		
//		Valid longitude values are between -180 and 180, both inclusive.
//	    Valid latitude values are between -90 and 90, both inclusive.

		if(location==null) {
			return false;
		}
		
		if(location.longitude >=-180
		&& location.longitude <=180
		&& location.latitude >=-90
		&& location.latitude <=90) {
			return true;
		}else
		return false;
	}

}
