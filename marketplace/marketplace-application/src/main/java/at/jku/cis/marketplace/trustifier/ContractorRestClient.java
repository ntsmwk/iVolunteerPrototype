package at.jku.cis.marketplace.trustifier;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.marketplace.participant.profile.CompetenceEntry;
import at.jku.cis.marketplace.participant.profile.TaskEntry;
import at.jku.cis.marketplace.task.Task;

@Service
public class ContractorRestClient {

	private static final String TRUSTIFIER_CONTRACTOR_TASK = "{0}/trustifier/contractor/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(Task task) {
		String requestURI = buildContractorRequestURI("task");
		return restTemplate.postForObject(requestURI, task, String.class);
	}

	public String publishTaskEntry(TaskEntry taskEntry) {
		String requestURI = buildContractorRequestURI("taskEntry");
		return restTemplate.postForObject(requestURI, taskEntry, String.class);
	}

	public String publishCompetenceEntry(CompetenceEntry competenceEntry) {
		String requestURI = buildContractorRequestURI("competenceEntry");
		return restTemplate.postForObject(requestURI, competenceEntry, String.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(TRUSTIFIER_CONTRACTOR_TASK, trustifierURI, requestPath);
	}

}
