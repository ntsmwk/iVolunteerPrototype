package at.jku.cis.iVolunteer.lib.rest.clients;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@Service
public class VerifierRestClient {

	private static final String TASK_INTERACTION = "taskInteraction";
	private static final String PUBLISHED_TASK = "publishedTask";
	private static final String FINISHED_TASK_Entry = "finishedTaskEntry";
	private static final String COMPETENCE_ENTRY = "competenceEntry";

	private static final String VERIFIER_URI = "{0}/trustifier/verifier/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public boolean verifyPublishedTask(Task task) {
		String requestURI = buildContractorRequestURI(PUBLISHED_TASK);
		return restTemplate.postForObject(requestURI, task, Boolean.class).booleanValue();
	}

	public boolean verifyTaskInteraction(TaskInteraction taskInteraction) {
		String requestURI = buildContractorRequestURI(TASK_INTERACTION);
		return restTemplate.postForObject(requestURI, taskInteraction, Boolean.class).booleanValue();
	}

	public boolean verifyFinishedTask(TaskEntry taskEntry) {
		String requestURI = buildContractorRequestURI(FINISHED_TASK_Entry);
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
