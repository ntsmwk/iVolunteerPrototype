package at.jku.csi.ivolunteer;


import java.util.List;
import at.jku.csi.blockchain.BlockchainRestClient;
import at.jku.csi.crypto.GlobalHash;
import at.jku.csi.crypto.HashGenerator;
import at.jku.csi.crypto.SimpleHash;

public class BlockchainCryptoTest {

	public static void main(String[] args) {
		BlockchainRestClient bc = new BlockchainRestClient("http://localhost:3000");
		HashGenerator hg = new HashGenerator();

		String testCompetency1 = new String(
				"[{id:1, domain:Rotes Kreuz Ö, name:Kompetenz X, receiveTimestamp:Monday, 26-Feb-18 13:26:14 UTC}]");
		SimpleHash s1 = new SimpleHash(hg.sha256(testCompetency1));
		
		bc.postSimpleHash(s1);

		GlobalHash gh1 = new GlobalHash("userX", hg.sha256("local repository of userX"));
		bc.postGlobalHash(gh1);

		List<SimpleHash> simpleHashes = bc.getSimpleHash(hg.sha256(testCompetency1));
		for (SimpleHash h : simpleHashes) {
			System.out.println(h.toString());
		}

		List<GlobalHash> globalHashes = bc.getGlobalHash("userX");
		for (GlobalHash h : globalHashes) {
			System.out.println(h.toString());
		}
	}
}
