package at.jku.cis.iVolunteer.workflow.rest.client;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class MarketplaceRestClient extends RestClient {

	@Value("${marketplace.uri}")
	private URI marketplaceURI;

	@Autowired
	private RestTemplate restTemplate;

	public List<VolunteerDTO> findVolunteers(String authorization) {
		String requestURI = buildMarketplaceRequestURI("/volunteer");
		ParameterizedTypeReference<List<VolunteerDTO>> typeReference = new ParameterizedTypeReference<List<VolunteerDTO>>() {
		};
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), typeReference).getBody();
	}

	public VolunteerDTO findVolunteerByID(String volunteerId, String authorization) {
		String requestURI = buildMarketplaceRequestURI("/volunteer/" + volunteerId);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), VolunteerDTO.class)
				.getBody();
	}
	
	public VolunteerDTO findVolunteerByUserName(String volunteerUsername, String authorization) {
		String requestURI = buildMarketplaceRequestURI("/volunteer/username/" + volunteerUsername);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), VolunteerDTO.class)
				.getBody();
	}

	public TaskDTO findTaskById(String taskId, String authorization) {
		String requestURI = buildMarketplaceRequestURI("/task/" + taskId);
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), TaskDTO.class).getBody();
	}

	public SourceDTO findSource(String authorization) {
		String requestURI = buildMarketplaceRequestURI("/source");
		return restTemplate.exchange(requestURI, HttpMethod.GET, buildEntity(authorization), SourceDTO.class).getBody();
	}

	public void publishTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/publish");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void startTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/start");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	private String buildMarketpaceTaskRequestURI(String requestPath) {
		return buildMarketplaceRequestURI("/task/") + requestPath;
	}

	private String buildMarketplaceRequestURI(String requestPath) {
		return marketplaceURI + requestPath;
	}
}
