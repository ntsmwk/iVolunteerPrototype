package at.jku.cis.iVolunteer.marketplace.task;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerCompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerTaskEntryDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class ContractorPublishingRestClient {

	private static final String TASK = "task";
	private static final String FINISHED_TASK_ENTRY = "finishedTaskEntry";
	private static final String COMPETENCE_ENTRY = "competenceEntry";
	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/{1}";


	private static final String AUTHORIZATION = "Authorization";
	
	@Value("${trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(TaskDTO task, String authorization) {
		String requestURI = buildContractorRequestURI(TASK);
		return restTemplate.postForObject(requestURI, buildEntity(task, authorization), String.class);
	}

	public String publishTaskEntry(VolunteerTaskEntryDTO vte, String authorization) {
		String requestURI = buildContractorRequestURI(FINISHED_TASK_ENTRY);
		return restTemplate.postForObject(requestURI, buildEntity(vte, authorization), String.class);
	}

	public String publishCompetenceEntry(VolunteerCompetenceEntryDTO vce, String authorization) {
		String requestURI = buildContractorRequestURI(COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, buildEntity(vce, authorization), String.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
	
	private HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}
}
