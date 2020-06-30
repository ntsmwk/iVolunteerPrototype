package at.jku.cis.iVolunteer.marketplace.user;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class VolunteerService {
	
	public int currentAge(Volunteer volunteer) {
		LocalDate birthDay = volunteer.getBirthday().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		Period diff = Period.between(birthDay, LocalDate.now());
		return diff.getYears();
	}
	
	public String typeOfService(Volunteer volunteer, String tenantId) {
		// XXX to do --> function
		return "EA";
	}
	
	public String currentRank(Volunteer volunteer, String tenantId) {
		// XXX to do --> function
		return " ";
	}
	
}
