package at.jku.cis.iVolunteer.lib.rest.clients;

import static java.text.MessageFormat.format;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.lib.rest.RestUtils;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@Service
public class MarketplaceRestClient {

	private static final String TASK_CONTROLLER_URI = "{0}/task/{1}";

	@Value("${marketplace.uri}")
	private URI marketplaceURI;

	@Autowired
	private RestTemplate restTemplate;

	public List<VolunteerDTO> findVolunteers(String authorization) {
		String requestURI = buildMarketplaceRequestURI("/volunteer");

		ParameterizedTypeReference<List<VolunteerDTO>> typeReference = new ParameterizedTypeReference<List<VolunteerDTO>>() {
		};
		
		return restTemplate.exchange(requestURI, HttpMethod.GET, RestUtils.buildEntity(authorization), typeReference)
				.getBody();
	}

	public void publishTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/publish");
		restTemplate.postForObject(requestURI, RestUtils.buildEntity(authorization), Void.class);
	}

	public void startTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/start");
		restTemplate.postForObject(requestURI, RestUtils.buildEntity(authorization), Void.class);
	}

	public void suspendTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/suspend");
		restTemplate.postForObject(requestURI, RestUtils.buildEntity(authorization), Void.class);
	}

	public void resumeTask(String taskId, String authorization) {
		String requestURI = buildMarketpaceTaskRequestURI(taskId + "/resume");
		restTemplate.postForObject(requestURI, RestUtils.buildEntity(authorization), Void.class);
	}

	private String buildMarketplaceRequestURI(String requestPath) {
		return marketplaceURI + requestPath;
	}

	private String buildMarketpaceTaskRequestURI(String requestPath) {
		return format(TASK_CONTROLLER_URI, marketplaceURI, requestPath);
	}
}
