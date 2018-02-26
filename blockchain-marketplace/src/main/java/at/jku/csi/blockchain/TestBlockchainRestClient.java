package at.jku.csi.blockchain;

import java.util.List;

public class TestBlockchainRestClient {

	public static void main(String[] args) {
		BlockchainRestClient bc = new BlockchainRestClient("http://localhost:3000");

		SimpleHash sh1 = new SimpleHash("xyz");
		bc.postSimpleHash(sh1);

		GlobalHash gh1 = new GlobalHash("user1", "abc");
		bc.postGlobalHash(gh1);

		List<SimpleHash> simpleHashes = bc.getSimpleHash("xyz");
		for (SimpleHash h : simpleHashes) {
			System.out.println(h.toString());
		}

		List<GlobalHash> globalHashes = bc.getGlobalHash("user1");
		for (GlobalHash h : globalHashes) {
			System.out.println(h.toString());
		}

	}

}
