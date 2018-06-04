package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.util.Date;

public class TestBc {

	public static void main(String[] args) {
		BlockchainRestClient bc = new BlockchainRestClient();
		
		
		bc.postPublishedTaskHash("test3", new Date(), "test3", "test3");
		
		BcPublishedTask t = bc.getPublishedTaskHash("test3");

		
		System.out.println(t.toString());
		
		
		
	}

}
