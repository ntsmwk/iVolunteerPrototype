package at.jku.cis.iVolunteer.marketplace.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.hash.IHashObject;

@Service
public class HashGenerator {

	private static final String ALGORITHM = "SHA-256";

	public String sha256(IHashObject object) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(object.toHashObject().getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return bytesToHex(messageDigest.digest());
	}

	private String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}

}
