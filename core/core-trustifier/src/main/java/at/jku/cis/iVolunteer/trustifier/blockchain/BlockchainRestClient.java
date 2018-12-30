
package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BlockchainRestClient.class);


	public BcPublishedTask getPublishedTask(Task task) {
		String requestUrl = MessageFormat.format("{0}/api/GetPublishedTask", url);
		try {
			BcPublishedTask[] arr = restTemplate.postForObject(requestUrl, task.getProperties(), BcPublishedTask[].class);
			return (arr.length > 0) ? arr[0] : null;
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return null;
	}

	public BcTaskInteraction getTaskInteraction(TaskInteraction task) {
		String requestUrl = MessageFormat.format("{0}/api/GetTaskInteraction", url);
		try {
			BcTaskInteraction[] arr = restTemplate.postForObject(requestUrl, task.getTaskInteractionProperties(), BcTaskInteraction[].class);
			return (arr.length > 0) ? arr[0] : null;
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return null;
	}

	public BcFinishedTask getFinishedTask(TaskEntry entry) {
		String requestUrl = MessageFormat.format("{0}/api/GetFinishedTask", url);
		try {
			BcFinishedTask[] arr = restTemplate.postForObject(requestUrl, entry.getProperties(), BcFinishedTask[].class);
			return (arr.length > 0) ? arr[0] : null;
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return null;
	}

	public BcCompetence getCompetence(CompetenceEntry entry) {
		String requestUrl = MessageFormat.format("{0}/api/GetCompetence", url);
		try {
			BcCompetence[] arr = restTemplate.postForObject(requestUrl, entry.getProperties(), BcCompetence[].class);
			return (arr.length > 0) ? arr[0] : null;
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	public void postPublishedTask(Task task) {
		String requestUrl = MessageFormat.format("{0}/api/AddNewPublishedTask", url);
		try {
			restTemplate.postForObject(requestUrl, task.getProperties(), Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
	}

	public void postTaskInteraction(TaskInteraction task) {
		String requestUrl = MessageFormat.format("{0}/api/AddNewTaskInteraction", url);
		try {
			restTemplate.postForObject(requestUrl, task.getTaskInteractionProperties(), Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
	}

	public void postFinishedTask(TaskEntry entry) {
		String requestUrl = MessageFormat.format("{0}/api/AddNewFinishedTask", url);
		try {
			restTemplate.postForObject(requestUrl, entry.getProperties(), Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
	}

	public void postCompetence(CompetenceEntry entry) {
		String requestUrl = MessageFormat.format("{0}/api/AddNewCompetence", url);
		try {
			restTemplate.postForObject(requestUrl, entry.getProperties(), String.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
	}

}
