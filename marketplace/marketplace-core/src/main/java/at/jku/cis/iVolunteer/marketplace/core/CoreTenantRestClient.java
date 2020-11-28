package at.jku.cis.iVolunteer.marketplace.core;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

@Service
public class CoreTenantRestClient {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${core.uri}")
	private String url;
	private static String AUTHORIZATION = "authorization";

	private static Logger logger = LoggerFactory.getLogger(CoreTenantRestClient.class);

	public String getTenantIdByName(String tenantName) {
		String requestUrl = MessageFormat.format("{0}/tenant/name/{1}", url, tenantName);
		String tenantId = null;
		try {
			tenantId = restTemplate.getForObject(requestUrl, String.class);
		} catch (Exception e) {
			this.handleException(e);
		}

		return tenantId;
	}

	public List<Tenant> getAllTenants() {
		String requestUrl = MessageFormat.format("{0}/tenant", url);
		Tenant[] tenants = null;

		try {
			tenants = restTemplate.getForObject(requestUrl, Tenant[].class);
		} catch (Exception e) {
			this.handleException(e);
		}

		return Arrays.asList(tenants);
	}

	public Tenant getTenantById(String tenantId, String authorization) {
		String requestUrl = MessageFormat.format("{0}/tenant/{1}/not-x", url, tenantId);
		Tenant tenant = null;
		try {
			ResponseEntity<Tenant> response = restTemplate.exchange(requestUrl, HttpMethod.GET,
					buildEntity("", authorization), Tenant.class);
			tenant = response.getBody();
		} catch (Exception e) {
			this.handleException(e);
		}

		return tenant;
	}

	private void handleException(Exception e) {
		if (e instanceof HttpStatusCodeException) {
			logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
		} else {
			logger.error(e.getMessage());

		}
	}

	private HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

}
