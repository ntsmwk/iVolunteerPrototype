package at.jku.cis.iVolunteer.core.aggregate;

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

import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;

@Service
public class AggregateDataRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestTemplate restTemplate;
	

	public List<XTaskCertificate> getClassAndTaskInstances(String marketplaceURL, String authorization, int year, GetClassAndTaskInstancesRequest body) {
		String preUrl = "{0}/aggregate/class-and-task-instance?startYear={1}";
		String url = format(preUrl, marketplaceURL, "" + year);
		ResponseEntity<XTaskCertificate[]> resp = restTemplate.exchange(url, HttpMethod.PUT, buildEntity(body, authorization), XTaskCertificate[].class);
		List<XTaskCertificate> ret = Arrays.asList(resp.getBody());
		return ret;
	}
	

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