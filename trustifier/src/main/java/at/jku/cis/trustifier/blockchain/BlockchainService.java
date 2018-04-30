
package at.jku.cis.trustifier.blockchain;

import static java.text.MessageFormat.format;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockchainService {

	@Value("${spring.data.blockchain.uri}")
	private URI uri;

	@Autowired
	private RestTemplate restTemplate;

	public boolean isSimpleHashInBc(String hash) {
		String requestUrl = format("{0}/api/queries/findSimpleHash?hash={1}", uri, hash);
		ResponseEntity<SimpleHash[]> responseEntity = restTemplate.getForEntity(requestUrl, SimpleHash[].class);

		SimpleHash[] objects = responseEntity.getBody();
		if (Arrays.asList(objects).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public SimpleHash postSimpleHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/simpleHash", uri);
		return restTemplate.postForObject(requestUrl, hash, SimpleHash.class);
	}

	public void deleteSimpleHash(String hash) {
		restTemplate.delete(MessageFormat.format("{0}/api/simpleHash/{1}", uri, hash));
	}
}
