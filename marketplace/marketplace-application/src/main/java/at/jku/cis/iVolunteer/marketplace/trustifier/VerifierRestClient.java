package at.jku.cis.iVolunteer.marketplace.trustifier;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.marketplace.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.marketplace.task.Task;

@Service
public class VerifierRestClient {

	private static final String TASK = "task";
	private static final String TASK_ENTRY = "taskEntry";
	private static final String COMPETENCE_ENTRY = "competenceEntry";

	private static final String VERIFIER_URI = "{0}/trustifier/verifier/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public boolean verifyTask(Task task) {
		String requestURI = buildContractorRequestURI(TASK);
		return restTemplate.postForObject(requestURI, task, Boolean.class).booleanValue();
	}

	public boolean verifyTaskEntry(TaskEntry taskEntry) {
		String requestURI = buildContractorRequestURI(TASK_ENTRY);
		return restTemplate.postForObject(requestURI, taskEntry, Boolean.class).booleanValue();
	}

	public boolean verifyCompetenceEntry(CompetenceEntry competenceEntry) {
		String requestURI = buildContractorRequestURI(COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, competenceEntry, Boolean.class).booleanValue();
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(VERIFIER_URI, trustifierURI, requestPath);
	}

}
