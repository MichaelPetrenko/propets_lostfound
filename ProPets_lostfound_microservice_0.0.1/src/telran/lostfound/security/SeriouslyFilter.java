package telran.lostfound.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class SeriouslyFilter implements Filter{
	
//	@Autowired
//	ILostFoundManagement lostfound;

//	public String[] decompileToken(String token) {
//		String secret = "ProPetsEvgeniiMichael19911995";
//		Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
//		Claims claims = jws.getBody();
//		String[] res = new String[4];
//		res[0] = claims.get("login").toString();
//		res[1] = claims.get("password").toString();
//		res[2] = claims.get("timestamp").toString();
//		String roles = claims.get("role").toString();
//		res[3] = roles.substring(1, roles.length()-1);
//		//[login, pass, 3463457, "USER, ADMIN"]
//		return res;
//	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		
		if (path.matches("/lostfound/en/v1/update/[^/]+")
		 || path.matches("/lostfound/en/v1/delete/[^/]+")
		 ){
			System.out.println("==============INSIDE ADVANCED FILTER!!!!!!!!!!!!!!!!!================");
//			String xToken = request.getHeader("X-Token");
//			String[] decompiledToken = decompileToken(xToken);
//			
//			String postPath = request.getContextPath().split("/")[5];
//			System.out.println("=================================="+postPath);
//			ResponsePostDto post = lostfound.postById(postPath);
//			if(!decompiledToken[0].equals(post.userLogin)) {
//				response.sendError(403);
//				return;
//			}
//			
			chain.doFilter(request, response);
			return;
		}
	}
	
}
