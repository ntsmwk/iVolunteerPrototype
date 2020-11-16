package at.jku.cis.iVolunteer.core.badge;

import static java.text.MessageFormat.format;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;

@Service
public class XBadgeCerticicateRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired private RestTemplate restTemplate;

//	TODO
	public List<TaskInstance> getXBadgeTemplates(String marketplaceURL, String taskType, int startYear, String status,
			String authorization) {
		String preUrl = "{0}/meta/core/task-instance/all?taskType={1}&startYear={2}&status={3}";
		String url = format(preUrl, marketplaceURL, taskType, startYear, status);

		ResponseEntity<TaskInstance[]> resp = restTemplate.exchange(url, HttpMethod.GET,
				buildEntity(null, authorization), TaskInstance[].class);
		if (resp == null || resp.getBody() == null) {
			return null;
		}
		return Arrays.asList(resp.getBody());
	}
	
//	TODO
	
	private HttpEntity<?> buildEntity(Object body, String authorization) {
		HttpEntity<?> entity = new HttpEntity<>(body, buildHeaders(authorization));
		return entity;
	}

	private HttpHeaders buildHeaders(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}


}
