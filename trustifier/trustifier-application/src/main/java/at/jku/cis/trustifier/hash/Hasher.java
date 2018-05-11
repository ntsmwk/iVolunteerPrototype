package at.jku.cis.trustifier.hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Hasher {
	
	@Autowired
	private HashGenerator hashObjectGenerator;
	
	public String generateHash(IHashObject hashObject) {
		return hashObjectGenerator.sha256(hashObject);
	}

}
