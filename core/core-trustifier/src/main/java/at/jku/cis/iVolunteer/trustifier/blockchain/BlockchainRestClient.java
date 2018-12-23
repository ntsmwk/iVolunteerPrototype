
package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BlockchainRestClient.class);

//	CompetenceEntry competenceEntry;

//	@PostConstruct
//	public void init() {
//		competenceEntry = new CompetenceEntry("", "competenceId01", "competenceName01", "marketplace01", "volunteer1",
//				new Date());
//
//	}

	public BcPublishedTask getPublishedTask(TaskInteraction task) { // TaskInteraction task
		String requestUrl = MessageFormat.format("{0}/api/GetPublishedTask", url);
		try {
			BcPublishedTask[] arr = restTemplate.postForObject(requestUrl, task.getProperties(false), BcPublishedTask[].class);
//			if (arr.length > 0) {
//				System.out.println(arr[0].getHash());
//				System.out.println(arr[0].getMarketplaceId());
//				System.out.println(arr[0].getTimestamp());
//			}
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
			BcTaskInteraction[] arr = restTemplate.postForObject(requestUrl, task.getProperties(true), BcTaskInteraction[].class);
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
//			if (arr.length > 0) {
//				System.out.println(arr[0].getHash());
//				System.out.println(arr[0].getMarketplaceId());
//				System.out.println(arr[0].getVolunteerId());
//				System.out.println(arr[0].getTimestamp());
//			}
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
//			if (arr.length > 0) {
//				System.out.println(arr[0].getHash());
//				System.out.println(arr[0].getCompetenceId());
//				System.out.println(arr[0].getMarketplaceId());
//				System.out.println(arr[0].getVolunteerId());
//				System.out.println(arr[0].getTimestamp());
//			}
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

	public void postPublishedTask(TaskInteraction task) { // TaskInteraction task
		String requestUrl = MessageFormat.format("{0}/api/AddNewPublishedTask", url);
		try {
			restTemplate.postForObject(requestUrl, task.getProperties(false), Void.class);
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
			restTemplate.postForObject(requestUrl, task.getProperties(true), Void.class);
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

	// only for testing, to be deleted!
	public void postNewMarketplace(String marketplace) {
		String requestUrl = MessageFormat.format("{0}/api/AddNewMarketplace", url);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("marketplaceId", marketplace);
		try {
			restTemplate.postForObject(requestUrl, map, String.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());
			}
		}
	}
}
