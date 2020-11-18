package at.jku.cis.iVolunteer.core.badge;

import static java.text.MessageFormat.format;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;

@Service
public class XBadgeTemplateRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired private RestTemplate restTemplate;

	public Map<String, List<XBadgeTemplate>> getXBadgeTemplates(String marketplaceURL, List<String> tenantIds,
			String authorization) {
		String preUrl = "{0}/badgeTemplate";
		String url = format(preUrl, marketplaceURL);

		ResponseEntity<Map> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(tenantIds, authorization),
				Map.class);
		if (resp == null || resp.getBody() == null) {
			return null;
		}
		return resp.getBody();
	}

	public void createBadgeTemplate(String marketplaceURL, XBadgeTemplate badgeTemplate) {
		String preUrl = "{0}/badgeTemplate/init";
		String url = format(preUrl, marketplaceURL);

		restTemplate.exchange(url, HttpMethod.POST, buildEntity(badgeTemplate, ""), ResponseEntity.class);
	}

	private HttpEntity<?> buildEntity(Object body, String authorization) {
		HttpEntity<?> entity = new HttpEntity<>(body, buildHeaders(authorization));
		return entity;
	}

	private HttpHeaders buildHeaders(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
