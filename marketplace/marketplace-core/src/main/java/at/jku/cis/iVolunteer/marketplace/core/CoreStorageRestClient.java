package at.jku.cis.iVolunteer.marketplace.core;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model._httprequests.Base64ImageUploadRequest;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;

@Service
public class CoreStorageRestClient {

	@Autowired private RestTemplate restTemplate;
	@Value("${core.uri}") private String url;
	private static String AUTHORIZATION = "authorization";

	private static Logger logger = LoggerFactory.getLogger(CoreStorageRestClient.class);

	public ResponseEntity<StringResponse> storeImageBase64(String filename, String image, String authorization) {
		String requestUrl = MessageFormat.format("{0}/file/image-base64", url);
		ResponseEntity<StringResponse> resp = restTemplate.exchange(requestUrl, HttpMethod.POST,
				buildEntity(new Base64ImageUploadRequest(filename, image), authorization), StringResponse.class);
		return resp;
	}

	private void handleException(Exception e) {
		if (e instanceof HttpStatusCodeException) {
			logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
		} else {
			logger.error(e.getMessage());

		}
	}

	private HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

}
