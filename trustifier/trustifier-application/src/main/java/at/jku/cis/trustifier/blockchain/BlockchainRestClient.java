
package at.jku.cis.trustifier.blockchain;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BlockchainRestClient.class);

	public boolean isCompetenceEntryHashInBc(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/CompetenceEntryHashInBc/{1}", url, hash);
		try {
			restTemplate.getForObject(requestUrl, String.class);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Hash postCompetenceEntryHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/CompetenceEntryHash", url);
		Hash h = new Hash(hash);

		try {
			restTemplate.postForObject(requestUrl, h, Hash.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return h;
	}

	public void deletCeompetenceEntryHash(String hash) {
		try {
			restTemplate.delete(MessageFormat.format("{0}/api/CompetenceEntryHash/{1}", url, hash));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public boolean isTaskEntryHashInBc(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/TaskEntryHash/{1}", url, hash);
		try {
			restTemplate.getForObject(requestUrl, String.class);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Hash postTaskEntryHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/TaskEntryHash", url);
		Hash h = new Hash(hash);

		try {
			restTemplate.postForObject(requestUrl, h, Hash.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return h;
	}

	public void deleteTaskEntryHash(String hash) {
		try {
			restTemplate.delete(MessageFormat.format("{0}/api/TaskEntryHash/{1}", url, hash));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public boolean isTaskInteractionHashInBc(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/TaskInteractionHash/{1}", url, hash);
		try {
			restTemplate.getForObject(requestUrl, String.class);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Hash postTaskInteractionHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/TaskInteractionHash", url);

		Hash h = new Hash(hash);

		try {
			restTemplate.postForObject(requestUrl, h, Hash.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return h;
	}

	public void deleteTaskInteractionHash(String hash) {
		try {
			restTemplate.delete(MessageFormat.format("{0}/api/TaskInteractionHash/{1}", url, hash));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
