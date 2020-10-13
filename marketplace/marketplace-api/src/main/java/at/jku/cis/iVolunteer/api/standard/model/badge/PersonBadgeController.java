package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/PersonBadges")
public class PersonBadgeController {

	@Autowired private PersonBadgeService personBadgeService;

	@PutMapping("/tenant/{tenantId}")
	public void savePersonBadge(@RequestBody List<PersonBadge> badges, @PathVariable("tenantId") String tenantId) {
		personBadgeService.savePersonBadges(badges, tenantId);
	}

}
