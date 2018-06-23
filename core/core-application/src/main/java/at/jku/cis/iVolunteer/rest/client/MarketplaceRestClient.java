package at.jku.cis.iVolunteer.rest.client;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class MarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";
	private static final String MARKETPLACE_TASK_URL = "{0}/task/{1}";

	@Autowired
	private RestTemplate restTemplate;

	public TaskDTO createTask(String marketplaceURL, String authorization, TaskDTO task) {
		String url = format(MARKETPLACE_TASK_URL, marketplaceURL);
		return restTemplate.postForObject(url, buildEntity(authorization), TaskDTO.class);
	}

	public List<TaskDTO> getTasks(String marketplaceURL, String authorization) {
		String url = format(MARKETPLACE_TASK_URL, marketplaceURL, "");
		return restTemplate.getForObject(url, List.class);
	}

	public TaskDTO getTaskById(String taskId, String marketplaceURL, String authorization) {
		String url = format(MARKETPLACE_TASK_URL, marketplaceURL, taskId);
		return restTemplate.getForObject(url, TaskDTO.class);
	}

	private HttpEntity<?> buildEntity(String authorization) {
		return new HttpEntity<>(null, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}
}
