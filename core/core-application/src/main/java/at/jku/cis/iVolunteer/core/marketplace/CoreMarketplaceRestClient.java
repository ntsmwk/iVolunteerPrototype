package at.jku.cis.iVolunteer.core.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.user.FlexProd;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.Recruiter;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class CoreMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	private static final String MARKETPLACE_REGISTER_VOLUNTEER = "{0}/volunteer";
	private static final String MARKETPLACE_REGISTER_HELP_SEEKER = "{0}/helpseeker";
	private static final String MARKETPLACE_REGISTER_RECRUITER = "{0}/recruiter";

	private static final String MARKETPLACE_REGISTER_FLEXPROD = "{0}/flexprod";

	@Autowired private RestTemplate restTemplate;

	public Volunteer registerVolunteer(String marketplaceURL, String authorization, Volunteer volunteer) {
		String url = format(MARKETPLACE_REGISTER_VOLUNTEER, marketplaceURL, volunteer);
		return restTemplate.postForObject(url, buildEntity(volunteer, authorization), Volunteer.class);
	}

	public HelpSeeker registerHelpSeeker(String marketplaceURL, String authorization, HelpSeeker helpSeeker) {
		String url = format(MARKETPLACE_REGISTER_HELP_SEEKER, marketplaceURL, helpSeeker);
		return restTemplate.postForObject(url, buildEntity(helpSeeker, authorization), HelpSeeker.class);
	}

	public Recruiter registerRecruiter(String marketplaceURL, String authorization, Recruiter recruiter) {
		String url = format(MARKETPLACE_REGISTER_RECRUITER, marketplaceURL, recruiter);
		return restTemplate.postForObject(url, buildEntity(recruiter, authorization), Recruiter.class);
	}

	public FlexProd registerFlexProdUser(String marketplaceURL, String authorization, FlexProd flexProdUser) {
		String url = format(MARKETPLACE_REGISTER_FLEXPROD, marketplaceURL, flexProdUser);
		return restTemplate.postForObject(url, buildEntity(flexProdUser, authorization), FlexProd.class);
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