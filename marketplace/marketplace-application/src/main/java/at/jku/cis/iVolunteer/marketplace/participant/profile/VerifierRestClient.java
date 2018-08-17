package at.jku.cis.iVolunteer.marketplace.participant.profile;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.participant.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.TaskEntryDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class VerifierRestClient {

	private static final String PUBLISHED_TASK = "publishedTask";
	private static final String PUBLISHED_TASK_Entry = "finishedTaskEntry";
	private static final String PUBLISHED_COMPETENCE_ENTRY = "competenceEntry";

	private static final String VERIFIER_URI = "{0}/trustifier/verifier/{1}";

	@Value("${trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public boolean verifyTask(TaskDTO task) {
		String requestURI = buildContractorRequestURI(PUBLISHED_TASK);
		return restTemplate.postForObject(requestURI, task, Boolean.class).booleanValue();
	}

	public boolean verifyTaskEntry(TaskEntryDTO taskEntry) {
		String requestURI = buildContractorRequestURI(PUBLISHED_TASK_Entry);
		return restTemplate.postForObject(requestURI, taskEntry, Boolean.class).booleanValue();
	}

	public boolean verifyCompetenceEntry(CompetenceEntryDTO competenceEntry) {
		String requestURI = buildContractorRequestURI(PUBLISHED_COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, competenceEntry, Boolean.class).booleanValue();
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(VERIFIER_URI, trustifierURI, requestPath);
	}

}
