package telran.lostfound.api;

public interface LostFoundApiConstants {
	
	String NEW_LOST_PET = 						"/lostfound/en/v1/lost/{login}";
	String NEW_FOUND_PET = 						"/lostfound/en/v1/found/{login}";
	String GET_POSTS_OF_LOST_PETS = 			"lostfound/en/v1/losts";
//	String GET_POSTS_OF_LOST_PETS = 			"lostfound/en/v1/losts?currentPage=0&itemsOnPage=5";
	String GET_POSTS_OF_FOUND_PETS = 			"/lostfound/en/v1/founds";
//	String GET_POSTS_OF_FOUND_PETS = 			"/lostfound/en/v1/founds?currentPage=0&itemsOnPage=2";
	String POST_BY_ID = 						"/lostfound/en/v1/{id}";
	String SEARCH_BY_INFORMATION_OF_FOUND_PET = "/lostfound/en/v1/founds/filter?currentPage=0&itemsOnPage=5";
//	String SEARCH_BY_INFORMATION_OF_FOUND_PET = "/lostfound/en/v1/founds/filter";
	String UPDATE_POST_FOUND_LOST_PET = 		"/lostfound/en/v1/{id}";
	String TAGS_AND_COLORS_OF_PICTURE = 		"/lostfound/en/v1/tagscolors/";
//	String TAGS_AND_COLORS_OF_PICTURE = 		"/lostfound/en/v1/tagscolors/?image_url=URL";
	String DELETE_POST_BY_ID = 					"https://propets-app.herokuapp.com/lostfound/en/v1/{id}";
	String GET_USER_DATA_LIST_ID = 				"/lostfound/en/v1/userdata"; 
	String SEARCH_BY_INFORMATION_OF_LOST_PET = 	"/lostfound/en/v1/lost/filter";
//	String SEARCH_BY_INFORMATION_OF_LOST_PET = 	"/lostfound/en/v1/lost/filter?currentPage=0&itemsOnPage=10";

}