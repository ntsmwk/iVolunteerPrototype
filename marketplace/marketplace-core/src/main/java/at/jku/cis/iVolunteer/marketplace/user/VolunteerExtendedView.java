package at.jku.cis.iVolunteer.marketplace.user;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.user.Volunteer;

// @Service
public class VolunteerExtendedView {
	
	private Volunteer volunteer;
	
	public VolunteerExtendedView(Volunteer volunteer) {
		System.out.println("setting up vol details ..... " + volunteer.getId());
		this.volunteer = volunteer;
	}
	
	public int currentAge() {
		System.out.println("volunteer.currentAge: " +
				 volunteer.getBirthday());
		LocalDate birthDay = volunteer.getBirthday().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		Period diff = Period.between(LocalDate.now(), birthDay);
		System.out.println("volunteer.currentAge: " +
				 diff.toString());
		return diff.getYears();
	}

}
