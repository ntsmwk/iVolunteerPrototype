package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.usermapping.UserMappingRepository;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.model.usermapping.UserMapping;

@RestController
public class InitializationController {

	@Autowired private UserMappingRepository userMappingRepository;
	@Autowired private VolunteerRepository volunteerRepository;	

	@PutMapping("/init/usermapping")
	public void addFireBrigadeUserMapping() {
		UserMapping userMapping = userMappingRepository.findByExternalUserId("5dc2a215399af");

		if (userMapping != null) {
			userMappingRepository.deleteAll();
		}

		Volunteer volunteer = volunteerRepository.findByUsername("mweixlbaumer");
		if (volunteer != null) {
			UserMapping mapping = new UserMapping();
			mapping.setiVolunteerUserId(volunteer.getId());
			mapping.setExternalUserId("5dc2a215399af");
			userMappingRepository.save(mapping);
		}
	}

}
