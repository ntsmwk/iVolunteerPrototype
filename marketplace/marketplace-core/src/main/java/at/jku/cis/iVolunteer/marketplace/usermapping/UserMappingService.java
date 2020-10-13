package at.jku.cis.iVolunteer.marketplace.usermapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.usermapping.UserMapping;

@Service
public class UserMappingService {

	@Autowired private UserMappingRepository userMappingRepository;

	public void saveUserMapping(UserMapping userMapping) {
		this.userMappingRepository.save(userMapping);
	}

	public UserMapping getByExternalUserId(String externalUserId) {
		return userMappingRepository.findByExternalUserId(externalUserId);
	}

}
