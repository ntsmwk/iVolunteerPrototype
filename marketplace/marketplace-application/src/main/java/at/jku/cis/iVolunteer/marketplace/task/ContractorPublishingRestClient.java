package at.jku.cis.iVolunteer.marketplace.task;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(TaskDTO task) {
		String requestURI = buildContractorRequestURI(TASK);
		return restTemplate.postForObject(requestURI, task, String.class);
	}

	public String publishTaskEntry(VolunteerTaskEntryDTO vte) {
		String requestURI = buildContractorRequestURI(FINISHED_TASK_ENTRY);
		return restTemplate.postForObject(requestURI, vte, String.class);
	}

	public String publishCompetenceEntry(VolunteerCompetenceEntryDTO vce) {
		String requestURI = buildContractorRequestURI(COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, vce, String.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
