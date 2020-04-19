package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
@RequestMapping("/volunteer")
public class CoreVolunteerController {

	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private CoreVolunteerService coreVolunteerService;

	@GetMapping("/all")
	public List<CoreVolunteer> getAllCoreVolunteers() {
		return this.coreVolunteerRepository.findAll();
	}

	@GetMapping("/{volunteerId}")
	public CoreVolunteer getCoreVolunteer(@PathVariable("volunteerId") String volunteerId) {
		return coreVolunteerRepository.findOne(volunteerId);
	}

	@GetMapping("/{volunteerId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("volunteerId") String volunteerId) {
		CoreVolunteer volunteer = coreVolunteerRepository.findOne(volunteerId);
		return volunteer.getRegisteredMarketplaces(); 
	}

	@PutMapping("/{volunteerId}")
	public void updateVolunteer(@RequestBody CoreVolunteer volunteer) {
		this.coreVolunteerRepository.save(volunteer);
	}
	
	@PostMapping("/{coreVolunteerId}/subscribe/{marketplaceId}/tenant/{tenantId}")
	public ResponseEntity<Void> subscribeTenant(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId, @RequestHeader("Authorization") String authorization) {
		coreVolunteerService.subscribeTenant(coreVolunteerId, marketplaceId,tenantId, authorization);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{coreVolunteerId}/unsubscribe/{marketplaceId}/tenant/{tenantId}")
	public ResponseEntity<Void> unsubscribeTenant(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId, @RequestHeader("Authorization") String authorization) {
		coreVolunteerService.unsubscribeTenant(coreVolunteerId, marketplaceId,tenantId, authorization);
		return ResponseEntity.ok().build();
	}

}
