package at.jku.csi.blockchain;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BlockchainRestClient {
	private String url;

	public BlockchainRestClient(String url) {
		this.url = url;
	}

	/*
	 * public List<SimpleHash> getSimpleHashes() { RestTemplate restTemplate = new
	 * RestTemplate(); ResponseEntity<SimpleHash[]> responseEntity =
	 * restTemplate.getForEntity(url + "/api/at.jku.cis.simpleHash",
	 * SimpleHash[].class); SimpleHash[] objects = responseEntity.getBody(); return
	 * Arrays.asList(objects); }
	 */

	public List<SimpleHash> getSimpleHash(String hash) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SimpleHash[]> responseEntity = restTemplate
				.getForEntity(url + "/api/queries/findSimpleHash?hash=" + hash, SimpleHash[].class);
		SimpleHash[] objects = responseEntity.getBody();
		return Arrays.asList(objects);

	}

	public void postSimpleHash(SimpleHash h) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.postForEntity(url + "/api/at.jku.cis.simpleHash", h, SimpleHash.class);
			System.out.println(h + " posted");

		} catch (Exception e) {
			System.out.println(h + " not posted, might already exist!");

		}
	}

	public List<GlobalHash> getGlobalHash(String userId) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<GlobalHash[]> responseEntity = restTemplate
				.getForEntity(url + "/api/queries/findGlobalHashByUserId?userId=" + userId, GlobalHash[].class);
		GlobalHash[] objects = responseEntity.getBody();
		return Arrays.asList(objects);

	}

	public void postGlobalHash(GlobalHash h) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.postForEntity(url + "/api/at.jku.cis.globalHash", h, SimpleHash.class);
			System.out.println(h + " posted");

		} catch (Exception e) {
			System.out.println(h + " not posted, might already exist!");

		}
	}

}
