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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.api.codes.NoContentException;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class AdvancedFilter implements Filter{
	
	@Autowired
	ILostFoundManagement lostfound;

	public String[] decompileToken(String token) {
		String secret = "ProPetsEvgeniiMichael19911995";
		Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		Claims claims = jws.getBody();
		String[] res = new String[4];
		res[0] = claims.get("login").toString();
		res[1] = claims.get("password").toString();
		res[2] = claims.get("timestamp").toString();
		String roles = claims.get("role").toString();
		res[3] = roles.substring(1, roles.length()-1);
		//[login, pass, 3463457, "USER, ADMIN"]
		return res;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		
		if (path.matches("/lostfound/en/v1/update/[^/]+")
		 || path.matches("/lostfound/en/v1/delete/[^/]+")
		 ){
			String xToken = request.getHeader("X-Token");
			if(xToken==null || xToken=="") {
				response.sendError(401);
				return;
			}
			String[] decompiledToken = new String[4];
			try {
				decompiledToken = decompileToken(xToken);
			} catch (Exception e) {
				response.sendError(401);
				return;
			}
			
			String postPath = request.getServletPath().split("/")[5];
			ResponsePostDto post = new ResponsePostDto();
			try {
				post = lostfound.postById(postPath);
			} catch (Exception e) {
				response.sendError(400);
				return;
			}
			if(!decompiledToken[0].equals(post.userLogin)) {
				response.sendError(403);
				return;
			}
			
			RestTemplate restTemplate = new RestTemplate();
			String endPointTags = "https://propets-me.herokuapp.com/account/en/v1/token/validation";
			URI uri;
			try {
				uri = new URI(endPointTags);
			} catch (Exception e) {
				throw new NoContentException();
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
			
			
		}
		chain.doFilter(request, response);
	}
	
}
