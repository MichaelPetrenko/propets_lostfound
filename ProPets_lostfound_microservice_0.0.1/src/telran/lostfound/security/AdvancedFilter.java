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
import telran.lostfound.api.ResponsePostDto;
import telran.lostfound.service.TokenValidationRequestor;
import telran.lostfound.service.interfaces.ILostFoundManagement;

@Service
public class AdvancedFilter implements Filter{
	
	@Autowired
	ILostFoundManagement lostfound;
	
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
			TokenValidationRequestor tvr = new TokenValidationRequestor();
			String[] credentials = tvr.decompileToken(xToken); 
			String id = request.getServletPath().split("/")[5];
			ResponsePostDto post = new ResponsePostDto();
			try {
				post = lostfound.postById(id);
			} catch (Exception e) {
				response.sendError(400);
				return;
			}
			if(!credentials[0].equals(post.userLogin)) {
				response.sendError(403);
				return;
			}
			try {
				String newToken = tvr.validateToken(xToken);
				response.setHeader("X-Token", newToken);
			} catch (Exception e) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}