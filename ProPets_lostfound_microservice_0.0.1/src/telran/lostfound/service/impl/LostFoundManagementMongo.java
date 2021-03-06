package telran.lostfound.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import telran.lostfound.api.codes.BadRequestException;
import telran.lostfound.api.codes.BadTokenException;
import telran.lostfound.api.codes.ForbiddenException;
import telran.lostfound.api.KafkaReqType;
import telran.lostfound.api.LocationDto;
import telran.lostfound.api.LocationIQDto;
import telran.lostfound.api.LostFoundKafkaDto;
import telran.lostfound.api.ResponsePagesDto;
import telran.lostfound.api.RequestLostFoundDto;
import telran.lostfound.api.ResponseLostFoundDto;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.RequestSearchDto;
import telran.lostfound.api.codes.BadURIException;
import telran.lostfound.api.codes.NoContentException;
import telran.lostfound.api.codes.NotExistsException;
import telran.lostfound.api.imaga.Color;
import telran.lostfound.api.imaga.ColorsApiResult;
import telran.lostfound.api.imaga.ColorsResult;
import telran.lostfound.api.imaga.Tag;
import telran.lostfound.api.imaga.Tags;
import telran.lostfound.api.imaga.TagsApiResult;
import telran.lostfound.dao.LostfoundRepository;
import telran.lostfound.domain.entities.Address;
import telran.lostfound.domain.entities.LostFoundEntity;
import telran.lostfound.service.KafkaLostFoundService;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class LostFoundManagementMongo implements ILostFoundManagement {

	private final String imaggaAuth = "Basic YWNjX2EwZDljMDBhNGM0MTEzYjpiZWNlYWU1YTdmODE3NTNhNmEzMzM2OWQxNzc3MWMwYg==";
	private final int contentTagsLength = 3;
	private final int colorTagsLength = 2;

	@Autowired
	LostfoundRepository repo;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	KafkaLostFoundService kafkaService;

	@Override
	public ResponseLostFoundDto newLostOrFoundPet(RequestLostFoundDto dto, String login, boolean lostOrFound,
			String xToken) {

		if (dto == null) {
			throw new NoContentException();
		}

		if (Double.compare(dto.location.latitude, 0) == 0 || Double.compare(dto.location.longitude, 0) == 0) {
			LocationDto locationFromAddress;

			try {
				locationFromAddress = addressToLocation(dto.address);
			} catch (Exception e) {
				throw new NoContentException();
			}

			dto.location = locationFromAddress;
		}

		if (dto.breed == null || dto.location == null || dto.tags == null || dto.type == null) {
			throw new NoContentException("Wrond data!");
		}

		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location");
		}
		;

		LostFoundEntity entity = new LostFoundEntity(dto, lostOrFound, login);
		repo.save(entity);

		String id = entity.getId();
		try {
			addPostToActivites(login, xToken, id);
		} catch (Exception e) {
			e.getStackTrace();
			if (e instanceof Forbidden) {
				throw new ForbiddenException();
			} else if (e instanceof Unauthorized) {
				System.out.println("if unauth case");
				throw new BadTokenException();
			} else if (e instanceof BadRequest) {
				throw new BadRequestException();
			} else
			throw new NotExistsException();
		}

		double[] coord = entity.getLocation();

		ResponseLostFoundDto resp = new ResponseLostFoundDto(entity.getId(), entity.getTypePost(),
				entity.getUserLogin(), entity.getUserName(), entity.getAvatar(), entity.getDatePost(), entity.getType(),
				entity.getSex(), entity.getBreed(), entity.getTags(), entity.getPhotos(), entity.getAddress(),
				new LocationDto(coord[0], coord[1]));
		
		LostFoundKafkaDto lfoKafka = new LostFoundKafkaDto(resp, KafkaReqType.CREATE);
		kafkaService.addPost(lfoKafka);

		return resp;
	}

	@Override
	public ResponsePostDto updatePost(RequestLostFoundDto dto, String postId) {
		if (dto == null) {
			throw new NoContentException();
		}
		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location!");
		}

		LostFoundEntity entity = repo.findById(postId).orElse(null);
		if (entity == null) {
			throw new NotExistsException();
		}
		entity.setType(dto.type);
		entity.setSex(dto.sex);
		entity.setBreed(dto.breed);
		entity.setAddress(dto.address);

		if (Double.compare(dto.location.latitude, 0) == 0 || Double.compare(dto.location.longitude, 0) == 0) {
			LocationDto locationFromAddress;

			try {
				locationFromAddress = addressToLocation(dto.address);
			} catch (Exception e) {
				throw new NoContentException();
			}

			dto.location = locationFromAddress;
		}
		
		double[] res = new double[2];
		res[0] = dto.location.longitude;
		res[1] = dto.location.latitude;
		entity.setLocation(res);
		entity.setPhotos(dto.photos);
		entity.setTags(dto.tags);
		repo.save(entity);

		ResponsePostDto req = new ResponsePostDto(entity);
		LostFoundKafkaDto lfoKafka = new LostFoundKafkaDto(req, KafkaReqType.UPDATE);
		kafkaService.addPost(lfoKafka);
		return req;
	}

	@Override
	public ArrayList<ResponsePostDto> getUserDataListId(String[] posts) {
		if (posts.length == 0) {
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
	public ResponsePostDto deletePostById(String postId, String xToken) {
		if (postId == null) {
			throw new NotExistsException();
		}
		ResponsePostDto resp = postById(postId);
		if (resp == null) {
			throw new NotExistsException();
		}
		try {
			removePostFromActivites(resp.userLogin, postId, xToken);
		} catch (Exception e) {
			e.getStackTrace();
			if (e instanceof Forbidden) {
				throw new ForbiddenException();
			} else if (e instanceof Unauthorized) {
				System.out.println("if unauth case");
				throw new BadTokenException();
			} else if (e instanceof BadRequest) {
				throw new BadRequestException();
			} else
			throw new NotExistsException();
		}
		repo.deleteById(resp.id);
		LostFoundKafkaDto lfoKafka = new LostFoundKafkaDto(resp, KafkaReqType.DELETE);
		kafkaService.addPost(lfoKafka);
		return resp;
	}

	private void removePostFromActivites(String userLogin, String postId, String xToken) {
		
		String endpointRemoveActivity = 
				"https://propets-me.herokuapp.com/"
				+ "en/v1/" 
				+ userLogin 
				+ "/activity/"																	
				+ postId;

		URI uri;
		try {
			uri = new URI(endpointRemoveActivity);
		} catch (Exception e) {
			System.out.println("Error URI");
			throw new BadURIException();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("X-Token", xToken);
		headers.set("X-ServiceName", "lostFound");
		
		HttpEntity<Void> request = new HttpEntity<>(headers);
		@SuppressWarnings("unused")
		ResponseEntity<Void> responceFromAddUserActivity = restTemplate.exchange(uri, HttpMethod.DELETE, request,
				Void.class);
		
	}

	private void addPostToActivites(String login, String xToken, String entityId) {

		String endpointAddActivity = 
				"https://propets-me.herokuapp.com/" 
				+ "en/v1/" 
				+ login 
				+ "/activity/"																	
				+ entityId;

		URI uri;
		try {
			uri = new URI(endpointAddActivity);
		} catch (Exception e) {
			System.out.println("Error URI");
			throw new BadURIException();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("X-Token", xToken);
		headers.set("X-ServiceName", "lostFound");

		HttpEntity<Void> request = new HttpEntity<>(headers);
		@SuppressWarnings("unused")
		ResponseEntity<Void> responceFromAddUserActivity = restTemplate.exchange(uri, HttpMethod.PUT, request,
				Void.class);
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
	public ResponsePagesDto searchInfoOfLostOrFound(RequestSearchDto dto, int items, int currentPage,
			boolean typePost) {

		if (!checkCorrectDataLocation(dto.location)) {
			throw new NoContentException("wrong data location!");
		}
		;

		Distance radiusOfSearch = new Distance(10, Metrics.KILOMETERS);
		Point pointOfSearch = new Point(dto.location.longitude, dto.location.latitude);
		Pageable pageable = PageRequest.of(currentPage, items);

		List<LostFoundEntity> allFounds = repo.findByLocationNearAndTypePostAndType(pointOfSearch, radiusOfSearch,
				typePost, dto.type, pageable);
		int itemsTotal = repo.findByLocationNearAndTypePostAndType(pointOfSearch, radiusOfSearch, typePost, dto.type)
				.size();
		ResponsePagesDto pDto = new ResponsePagesDto(items, currentPage, itemsTotal, allFounds);
		return pDto;
	}

	private boolean checkCorrectDataLocation(LocationDto location) {

//		Valid longitude values are between -180 and 180, both inclusive.
//	    Valid latitude values are between -90 and 90, both inclusive.

		if (location == null) {
			return false;
		}

		if (location.longitude >= -180 && location.longitude <= 180 && location.latitude >= -90
				&& location.latitude <= 90) {
			return true;
		} else
			return false;
	}

	private LocationDto addressToLocation(Address address) {

		String addressString = address.toString();
		String endPoint = "https://eu1.locationiq.com/v1/search.php?key=pk.9f0a8abe69f5422bb71ffebc3f77edde&q="
				+ addressString + "&format=json";
		RestTemplate restTemplate = new RestTemplate();

		URI uri;
		try {
			uri = new URI(endPoint);
		} catch (Exception e) {
			System.out.println("Error URI");
			e.printStackTrace();
			throw new BadURIException();
		}

		RequestEntity<Void> request = RequestEntity.get(uri).build();
		ResponseEntity<LocationIQDto[]> response = restTemplate.exchange(request, LocationIQDto[].class);
		LocationDto result = new LocationDto(response.getBody()[0].lon, response.getBody()[0].lat);

		return result;
	}

}
