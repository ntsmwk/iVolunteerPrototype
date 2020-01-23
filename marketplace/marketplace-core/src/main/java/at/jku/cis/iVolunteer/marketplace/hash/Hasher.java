package at.jku.cis.iVolunteer.marketplace.hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.hash.IHashObject;

@Service
public class Hasher {
	
	@Autowired
	private HashGenerator hashObjectGenerator;
	
	
	public String generateHash(IHashObject hashObject) {
		return hashObjectGenerator.sha256(hashObject);
	}

}
