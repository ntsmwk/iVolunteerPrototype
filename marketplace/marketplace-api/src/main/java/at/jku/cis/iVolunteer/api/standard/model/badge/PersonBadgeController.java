package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/PersonBadges")
public class PersonBadgeController {

	@Autowired private PersonBadgeService personBadgeService;

	@PutMapping
	public void savePersonBadge(@RequestBody List<PersonBadge> badges) {
		personBadgeService.savePersonBadges(badges);
	}

}
