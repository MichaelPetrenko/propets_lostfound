package telran.lostfound.service.impl;

import java.net.URI;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.lostfound.api.Location;
import telran.lostfound.api.PagesDto;
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
		
		//TODO Here we need to check Location - if not ex-s - to make it from address.
		
		
		LostFoundEntity entity = new LostFoundEntity(dto, lostOrFound, login);
		repo.save(entity);
		
//		double[] coord = entity.getLocation().getCoordinates();
		double[] coord = entity.getLocation();

		ResponseLostFoundDto resp = new ResponseLostFoundDto(
				entity.getId(), entity.getTypePost(),
				entity.getUserLogin(), entity.getUserName(), entity.getAvatar(), 
				entity.getDatePost(), entity.getType(),
				entity.getSex(), entity.getBreed(), entity.getTags(), 
				entity.getPhotos(), entity.getAddress(), new Location(coord[0], coord[1]));
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
		entity.setAddress(dto.address);
		
		double[] res = new double[2];
		res[0] = dto.location.longitude;
		res[1] = dto.location.latitude;
		entity.setLocation(res);
//		entity.setLocation(new GeolocationPointMongoDto(dto.location.longitude, dto.location.latitude));
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
	public String[] getTagsAndcolorsOfPicture(String imageLink) {
		try {
			String[] colors = gettingColorsOfImgFromImagga(imageLink);
			String[] tags = gettingTagsOfImgFromImagga(imageLink);
			String[] res = uniteArrays(tags, colors);
			return res;
		} catch (Exception e) {
			throw new NoContentException();
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
		RequestEntity<Void> request = RequestEntity
				.get(uri)
				.header("Authorization", imaggaAuth)
				.build();
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
		RequestEntity<Void> request = RequestEntity
				.get(uri)
				.header("Authorization", imaggaAuth)
				.build();
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
	public PagesDto getPostsOfLostPets(int items, int currentPage) {
		Pageable firstPage = PageRequest.of(currentPage, items);
		Page<LostFoundEntity> allProducts = repo.findAll(firstPage);
		int itemsTotal = (int) allProducts.getTotalElements();
//		allProducts.toList().toArray();
		
		ResponseLostFoundDto[] posts = (ResponseLostFoundDto[]) allProducts.toList().toArray();
		PagesDto pDto = new PagesDto(items, currentPage, itemsTotal, posts);
		
		return pDto;
	}

	@Override
	public ResponseGetPostsDto getPostsOfFoundPets(int items, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGetPostsDto searchInfoOfFound(int items, int currentPage) {
		//TODO
		return null;
	}

	@Override
	public ResponseGetPostsDto searchInfoOfLost(int items, int currentPage) {
		return null;
	}

//	Point point = new Point(35.35, 31.36);
//	Distance distance = new Distance(200, Metrics.KILOMETERS);
//	@SuppressWarnings("unused")
//	GeoResults<LostFoundEntity> res = repo.findByLocationNear(point, distance);

}
