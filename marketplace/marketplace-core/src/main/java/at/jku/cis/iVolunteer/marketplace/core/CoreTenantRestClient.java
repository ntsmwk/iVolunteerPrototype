package at.jku.cis.iVolunteer.marketplace.core;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

@Service
public class CoreTenantRestClient {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${core.uri}") private String url;

	private static Logger logger = LoggerFactory.getLogger(CoreTenantRestClient.class);

	public String getTenantIdByName(String tenantName) {
		String requestUrl = MessageFormat.format("{0}/tenant/name/{1}", url, tenantName);
		String tenantId = null;
		try {
			tenantId = restTemplate.getForObject(requestUrl, String.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

		return tenantId;
	}

	public Tenant getTenantById(String tenantId) {
		String requestUrl = MessageFormat.format("{0}/tenant/{1}", url, tenantId);
		Tenant tenant = null;
		try {
			tenant = restTemplate.getForObject(requestUrl, Tenant.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

		return tenant;
	}

}
