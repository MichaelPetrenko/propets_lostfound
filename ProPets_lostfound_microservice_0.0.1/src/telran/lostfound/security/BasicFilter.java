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

@Service
public class BasicFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();

		if (path.matches("/lostfound/en/v1/lost/[^/]+")
		 || path.matches("/lostfound/en/v1/found/[^/]+")
		 || path.matches("/lostfound/en/v1/losts")
		 || path.matches("/lostfound/en/v1/founds")
		 || path.matches("/lostfound/en/v1/founds/filter")
		 || path.matches("/lostfound/en/v1/losts/filter")
		 || path.matches("/lostfound/en/v1/post/[^/]+")
		 ) {
			
			System.out.println("==============INSIDE BASIC FILTER!!!!!!!!!!!!!!!!!================");	
			RestTemplate restTemplate = new RestTemplate();
				String endPointTags = "https://propets-me.herokuapp.com/account/en/v1/token/validation";
				URI uri;
				try {
					uri = new URI(endPointTags);
				} catch (Exception e) {
					throw new NoContentException();
				}
				String xToken = request.getHeader("X-Token");
				if(xToken==null || xToken=="") {
					response.sendError(401);
					return;
				}
				RequestEntity<Void> requestToValidation = RequestEntity.get(uri).header("X-Token", xToken ).build();
				try {
					ResponseEntity<String> responseFromValidation = restTemplate.exchange(requestToValidation, String.class);
					String newToken = responseFromValidation.getHeaders().get("X-Token").get(0);
					response.setHeader("X-Token", newToken);
				} catch (Exception e) {
					response.sendError(403);
					return;
				}
				chain.doFilter(request, response);
				return;
				
		}
		
	}
}
