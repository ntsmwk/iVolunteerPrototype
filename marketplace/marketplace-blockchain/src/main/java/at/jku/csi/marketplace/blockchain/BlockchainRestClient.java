
package at.jku.csi.marketplace.blockchain;

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
	private HashGenerator hashGenerator;

	public boolean isSimpleHashInBc(IHashObject hash) {
		ResponseEntity<SimpleHash[]> responseEntity = restTemplate.getForEntity(
				url + "/api/queries/findSimpleHash?hash=" + hashGenerator.sha256(hash), SimpleHash[].class);

		SimpleHash[] objects = responseEntity.getBody();
		if (Arrays.asList(objects).isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public void postSimpleHash(IHashObject hash) {
		SimpleHash s = new SimpleHash(hashGenerator.sha256(hash));

		try {
			System.out.println(hashGenerator.sha256(hash));
			restTemplate.postForObject(url + "/api/at.jku.cis.simpleHash", s, SimpleHash.class);

		} catch (Exception e) {
			System.out.println("Hash not posted, might already exist!");
		}
	}

	/*
	 * public List<GlobalHash> getGlobalHash(String userId) {
	 * ResponseEntity<GlobalHash[]> responseEntity = restTemplate .getForEntity(url
	 * + "/api/queries/findGlobalHashByUserId?userId=" + userId,
	 * GlobalHash[].class); return Arrays.asList(responseEntity.getBody());
	 * 
	 * }
	 * 
	 */
	/*
	 * public void postGlobalHash(GlobalHash h) { try {
	 * restTemplate.postForEntity(url + "/api/at.jku.cis.globalHash", h,
	 * SimpleHash.class); System.out.println(h + " posted");
	 * 
	 * } catch (Exception e) { System.out.println(h +
	 * " not posted, might already exist!");
	 * 
	 * } }
	 * 
	 */

}
