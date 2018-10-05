package at.jku.cis.iVolunteer.marketplace.volunteer.profile;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.TaskEntryDTO;

@Service
public class VerifierRestClient {

	private static final String PUBLISHED_TASK = "publishedTask";
	private static final String PUBLISHED_TASK_Entry = "finishedTaskEntry";
	private static final String PUBLISHED_COMPETENCE_ENTRY = "competenceEntry";

	private static final String VERIFIER_URI = "{0}/trustifier/verifier/{1}";

	private static final String AUTHORIZATION = "Authorization";

	@Value("${trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public boolean verifyTask(TaskDTO task, String authorization) {
		String requestURI = buildContractorRequestURI(PUBLISHED_TASK);
		return restTemplate.postForObject(requestURI, buildEntity(task, authorization), Boolean.class).booleanValue();
	}

	public boolean verifyTaskEntry(TaskEntryDTO taskEntry, String authorization) {
		String requestURI = buildContractorRequestURI(PUBLISHED_TASK_Entry);
		return restTemplate.postForObject(requestURI, buildEntity(taskEntry, authorization), Boolean.class)
				.booleanValue();
	}

	public boolean verifyCompetenceEntry(CompetenceEntryDTO competenceEntry, String authorization) {
		String requestURI = buildContractorRequestURI(PUBLISHED_COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, buildEntity(competenceEntry, authorization), Boolean.class)
				.booleanValue();
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(VERIFIER_URI, trustifierURI, requestPath);
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
