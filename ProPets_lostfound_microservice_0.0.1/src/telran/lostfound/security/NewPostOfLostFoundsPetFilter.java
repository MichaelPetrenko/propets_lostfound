package telran.lostfound.security;

import java.io.IOException;
import java.net.URI;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.lostfound.api.codes.NoContentException;
import telran.lostfound.api.imaga.TagsApiResult;

@Service
public class NewPostOfLostFoundsPetFilter implements Filter {
	
//	@Rest

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
//		String NEW_LOST_PET = 						"/lostfound/en/v1/lost/{login}";
//		String NEW_FOUND_PET = 						"/lostfound/en/v1/found/{login}";
		if (request.getServletPath().matches("/lostfound/en/v1/lost/[^/]+")
		 || request.getServletPath().matches("/lostfound/en/v1/found/[^/]+")) {
			
				RestTemplate restTemplate = new RestTemplate();
				String endPointTags = "https://propets-me.herokuapp.com/account/en/v1/token/validation";
				URI uri;
				try {
					uri = new URI(endPointTags);
				} catch (Exception e) {
					throw new NoContentException();
				}
				String xToken = request.getHeader("X-Token");
				RequestEntity<Void> requestToValidation = RequestEntity.get(uri).header("X-Token", xToken ).build();
				System.out.println(" = = = = trying get responce");
				ResponseEntity<Foo> responseFromValidation = restTemplate.exchange(requestToValidation, Foo.class);
				String newToken = responseFromValidation.getHeaders().get("X-Token").get(0);
				response.setHeader(xToken, newToken);
				System.out.println(" = = = = end of filter");
				return;
		}

	}
}
