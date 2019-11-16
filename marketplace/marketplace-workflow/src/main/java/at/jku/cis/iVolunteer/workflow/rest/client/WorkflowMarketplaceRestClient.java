package at.jku.cis.iVolunteer.workflow.rest.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.config.rest.client.configuration.WorkflowRestTemplate;
import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class WorkflowMarketplaceRestClient extends RestClient {

	@Autowired @WorkflowRestTemplate private RestTemplate restTemplate;

	public List<Volunteer> findVolunteers(String marketplaceUri, String authorization) {
		String requestURI = buildMarketplaceRequestURI(marketplaceUri, "/volunteer");
		ParameterizedTypeReference<List<Volunteer>> typeReference = new ParameterizedTypeReference<List<Volunteer>>() {
		};
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), typeReference).getBody();
	}

	public Volunteer findVolunteerById(String marketplaceUri, String volunteerId, String authorization) {
		String requestURI = buildMarketplaceRequestURI(marketplaceUri, "/volunteer/" + volunteerId);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), Volunteer.class).getBody();
	}

	public Volunteer findVolunteerByUserName(String marketplaceUri, String volunteerUsername, String authorization) {
		String requestURI = buildMarketplaceRequestURI(marketplaceUri, "/volunteer/username/" + volunteerUsername);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), Volunteer.class).getBody();
	}

	public Task findTaskById(String marketplaceUri, String taskId, String authorization) {
		String requestURI = buildMarketplaceRequestURI(marketplaceUri, "/task/" + taskId);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), Task.class).getBody();
	}

	public Source findSource(String marketplaceUri, String authorization) {
		String requestURI = buildMarketplaceRequestURI(marketplaceUri, "/source");
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), Source.class).getBody();
	}

	public void publishTask(String marketplaceUri, String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(marketplaceUri, taskId + "/publish");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void startTask(String marketplaceUri, String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(marketplaceUri, taskId + "/start");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	private String buildMarketpaceTaskRequestURI(String marketplaceUri, String requestPath) {
		return buildMarketplaceRequestURI(marketplaceUri, "/task/") + requestPath;
	}

	private String buildMarketplaceRequestURI(String marketplaceUri, String requestPath) {
		return marketplaceUri + requestPath;
	}
}
