
package at.jku.csi.marketplace.blockchain;

import static java.text.MessageFormat.format;

import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HashObjectGenerator hashGenerator;

	public boolean isSimpleHashInBc(IHashObject hashObject) {
		String hash = hashGenerator.sha256(hashObject);
		String requestUrl = format("{0}/api/queries/findSimpleHash?hash={1}", url, hash);
		ResponseEntity<SimpleHash[]> responseEntity = restTemplate.getForEntity(requestUrl, SimpleHash[].class);

		SimpleHash[] objects = responseEntity.getBody();
		if (Arrays.asList(objects).isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public void postSimpleHash(IHashObject hashObject) {
		SimpleHash simpleHash = new SimpleHash(hashGenerator.sha256(hashObject));

		try {
			String requestUrl = MessageFormat.format("{0}/api/simpleHash", url);
			restTemplate.postForObject(requestUrl, simpleHash, SimpleHash.class);

		} catch (Exception e) {
			System.out.println("Hash not posted, might already exist!");
			e.printStackTrace();
			// TODO: better exception handling
		}
	}

	public void deleteSimpleHash(IHashObject hashObject) {
		String hash = hashGenerator.sha256(hashObject);

		try {
			String requestUrl = MessageFormat.format("{0}/api/simpleHash/{1}", url, hash);
			restTemplate.delete(requestUrl);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: better exception handling
		}
	}
}
