package telran.lostfound.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class AdvancedFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		
		if (path.matches("/lostfound/en/v1/update/[^/]+")
		 || path.matches("/lostfound/en/v1/delete/[^/]+")
		 ){
			System.out.println("=============ADVANCED mat ego filter");
			chain.doFilter(request, response);
			return;
		}
	}
}
