package at.jku.cis.trustifier.marketplace;

import java.net.URI;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.trustifier.model.task.interaction.TaskInteraction;

@Service
public class MarketplaceService {

	@Value("${spring.data.marketplace.uri}")
	private URI uri;

	@Autowired
	private RestTemplate restTemplate;

	public TaskInteraction postTaskAssignment(TaskInteraction taskInteraction) {
		String requestUrl = MessageFormat.format("{0}/task/{1}/assign/{2}", uri, taskInteraction.getTask().getId(),
				taskInteraction.getParticipant().getId());
		return restTemplate.postForObject(requestUrl, null, TaskInteraction.class);
	}
}
