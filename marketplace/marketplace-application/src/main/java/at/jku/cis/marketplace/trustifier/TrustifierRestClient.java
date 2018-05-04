package at.jku.cis.marketplace.trustifier;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.marketplace.participant.Volunteer;
import at.jku.cis.marketplace.task.Task;

@Service
public class TrustifierRestClient {

	private static final String TRUSTIFIER_CONTRACTOR_TASK = "{0}/trustifier/contractor/task";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public String publishTask(Task task) {
		String requestURI = format(TRUSTIFIER_CONTRACTOR_TASK, trustifierURI);
		return restTemplate.postForObject(requestURI, task, String.class);
	}
}
