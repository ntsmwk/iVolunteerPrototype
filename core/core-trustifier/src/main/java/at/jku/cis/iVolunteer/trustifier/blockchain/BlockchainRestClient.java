
package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}") private String url;

	@Autowired private RestTemplate restTemplate;

	private static Logger logger = LoggerFactory.getLogger(BlockchainRestClient.class);

	public BcClassInstance getClassInstanceHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/verificationObject/{1}", url, hash);
		BcClassInstance c = null;
		try {
			c = restTemplate.getForObject(requestUrl, BcClassInstance.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return c;

	}

	public void postClassInstance(String hash, String marketplaceId, String userId) {
		String requestUrl = MessageFormat.format("{0}/api/verificationObject", url);
		BcClassInstance c = new BcClassInstance(hash, userId);
		try {
			restTemplate.postForObject(requestUrl, c, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
	}

	public void postClassInstanceArray(List<BcClassInstance> classInstances) {
		String requestUrl = MessageFormat.format("{0}/api/StoreVerificationObjects", url);
		try {

			BcClassInstances bcClassInstances = new BcClassInstances(classInstances.toArray(new BcClassInstance[0]));
			restTemplate.postForObject(requestUrl, bcClassInstances, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

	}
}
