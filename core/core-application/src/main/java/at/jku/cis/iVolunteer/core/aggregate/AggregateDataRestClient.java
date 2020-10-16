package at.jku.cis.iVolunteer.core.aggregate;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class AggregateDataRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestTemplate restTemplate;
	
	//TODO
	public List<GetClassAndTaskInstancesRequest> getClassAndTaskInstances(String marketplaceUrl, List<String> tenantIds, String userId, String authorization) {
		return new ArrayList<>();
	}
	
	public List<XTaskCertificate> getClassAndTaskInstances(String marketplaceURL, String authorization, GetClassAndTaskInstancesRequest body) {
		String preUrl = "{0}/aggregate/class-and-task-instance";
		String url = format(preUrl, marketplaceURL);
		System.out.println("Sending REsponse");
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