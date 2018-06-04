package at.jku.cis.iVolunteer.marketplace.task;

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
public class ContractorPublishingRestClient {

	private static final String TASK = "task";
	private static final String TASK_ENTRY = "taskEntry";
	private static final String COMPETENCE_ENTRY = "competenceEntry";
	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(TaskDTO taskDto) {
		String requestURI = buildContractorRequestURI(TASK);
		return restTemplate.postForObject(requestURI, taskDto, String.class);
	}

	public String publishTaskEntry(TaskEntryDTO taskEntry) {
		String requestURI = buildContractorRequestURI(TASK_ENTRY);
		return restTemplate.postForObject(requestURI, taskEntry, String.class);
	}

	public String publishCompetenceEntry(CompetenceEntryDTO competenceEntry) {
		String requestURI = buildContractorRequestURI(COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, competenceEntry, String.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
