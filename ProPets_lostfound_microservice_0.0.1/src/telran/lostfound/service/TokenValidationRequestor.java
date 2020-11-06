package telran.lostfound.service;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import telran.lostfound.api.codes.NoContentException;

public class TokenValidationRequestor {
	
	public String validateToken(String token) {
		
		String endPoint = "http://localhost:8082/validation/en/v1/validate";
		RestTemplate restTemplate = new RestTemplate();

		URI uri;
		try {
			uri = new URI(endPoint);
		} catch (Exception e) {
			throw new NoContentException();
		}

		RequestEntity<String> requestToValidateToken = RequestEntity.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.body(token);

		ResponseEntity<String> responceFromValidateToken = restTemplate.exchange
				(uri, HttpMethod.POST, requestToValidateToken, String.class);

		return responceFromValidateToken.getBody().toString();
	}
	
	public String[] decompileToken(String token) {
		String endPoint = "http://localhost:8082/validation/en/v1/decompile";
		RestTemplate restTemplate = new RestTemplate();

		URI uri;
		try {
			uri = new URI(endPoint);
		} catch (Exception e) {
			throw new NoContentException();
		}

		RequestEntity<String> requestToDecompile = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON)
				.body(token);

		ResponseEntity<String[]> responceFromDecompile = restTemplate.exchange(uri, HttpMethod.POST, 
				requestToDecompile, String[].class);
		return responceFromDecompile.getBody();
	}
}
