package at.jku.cis.iVolunteer.core.badge;

import static java.text.MessageFormat.format;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;

@Service
public class XBadgeCertificateRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired private RestTemplate restTemplate;

	public List<XBadgeCertificate> getXBadgeCertificates(String marketplaceURL, String authorization) {
		String preUrl = "{0}/badgeCertificates";
		String url = format(preUrl, marketplaceURL);

		ResponseEntity<XBadgeCertificate[]> resp = restTemplate.exchange(url, HttpMethod.GET,
				buildEntity(null, authorization), XBadgeCertificate[].class);
		if (resp == null || resp.getBody() == null) {
			return null;
		}
		return Arrays.asList(resp.getBody());
	}

	public List<XBadgeCertificate> getUnnotifiedXBadgeCertificates(String marketplaceURL, String authorization) {
		String preUrl = "{0}/badgeCertificates/unnotified";
		String url = format(preUrl, marketplaceURL);

		ResponseEntity<XBadgeCertificate[]> resp = restTemplate.exchange(url, HttpMethod.GET,
				buildEntity(null, authorization), XBadgeCertificate[].class);
		if (resp == null || resp.getBody() == null) {
			return null;
		}
		return Arrays.asList(resp.getBody());
	}
	
	public List<XBadgeCertificate> getXBadgeCertificatesByUserId(String marketplaceURL, String userId, String authorization) {
		String preUrl = "{0}/badgeCertificates/unnotified/{1}";
		String url = format(preUrl, marketplaceURL, userId);

		ResponseEntity<XBadgeCertificate[]> resp = restTemplate.exchange(url, HttpMethod.GET,
				buildEntity(null, authorization), XBadgeCertificate[].class);
		if (resp == null || resp.getBody() == null) {
			return null;
		}
		return Arrays.asList(resp.getBody());
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
