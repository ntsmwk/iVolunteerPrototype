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
public class ContractorRestClient {

	private static final String TASK = "task";
	private static final String TASK_ENTRY = "taskEntry";
	private static final String COMPETENCE_ENTRY = "competenceEntry";

	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(Task task) {
		String requestURI = buildContractorRequestURI(TASK);
		return restTemplate.postForObject(requestURI, task, String.class);
	}

	public String publishTaskEntry(TaskEntry taskEntry) {
		String requestURI = buildContractorRequestURI(TASK_ENTRY);
		return restTemplate.postForObject(requestURI, taskEntry, String.class);
	}

	public String publishCompetenceEntry(CompetenceEntry competenceEntry) {
		String requestURI = buildContractorRequestURI(COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, competenceEntry, String.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierURI, requestPath);
	}

}
