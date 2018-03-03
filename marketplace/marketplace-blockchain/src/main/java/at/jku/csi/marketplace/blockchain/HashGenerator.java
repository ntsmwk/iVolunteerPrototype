package at.jku.csi.marketplace.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	public String sha256(String data) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return bytesToHex(md.digest());

	}

	private String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}

}
