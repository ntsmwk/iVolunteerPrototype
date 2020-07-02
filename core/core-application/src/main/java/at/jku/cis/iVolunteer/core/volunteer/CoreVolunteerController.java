package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;
import java.util.stream.Collectors;

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

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

@RestController
@RequestMapping("/volunteer")
public class CoreVolunteerController {

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private CoreVolunteerService coreVolunteerService;
	@Autowired
	private CoreUserService coreUserService;

	@GetMapping("/all")
	public List<CoreUser> getAllCoreVolunteers() {
		return this.coreUserService.getCoreUsersByRole(UserRole.VOLUNTEER);
	}

	@GetMapping("/all/{tenantId}")
	public List<CoreUser> getAllCoreVolunteersByTenantId(@PathVariable String tenantId) {
		return this.coreUserService.getCoreUsersByRole(UserRole.VOLUNTEER).stream()
				.filter(vol -> vol.getSubscribedTenants().contains(tenantId)).collect(Collectors.toList());
	}

	@GetMapping("/{volunteerId}")
	public CoreUser getCoreVolunteer(@PathVariable("volunteerId") String volunteerId) {
		return coreUserRepository.findOne(volunteerId);
	}

	@GetMapping("/{volunteerId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("volunteerId") String volunteerId) {
		CoreUser volunteer = coreUserRepository.findOne(volunteerId);
		return volunteer.getRegisteredMarketplaces();
	}

	@PutMapping("/{volunteerId}")
	public void updateVolunteer(@RequestBody CoreUser volunteer) {
		this.coreUserRepository.save(volunteer);
	}

	@PostMapping("/{coreVolunteerId}/subscribe/{marketplaceId}/tenant/{tenantId}")
	public ResponseEntity<Void> subscribeTenant(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
			@RequestHeader("Authorization") String authorization) {

		coreVolunteerService.subscribeTenant(coreVolunteerId, marketplaceId, tenantId, authorization);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{coreVolunteerId}/unsubscribe/{marketplaceId}/tenant/{tenantId}")
	public ResponseEntity<Void> unsubscribeTenant(@PathVariable("coreVolunteerId") String coreVolunteerId,
			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
			@RequestHeader("Authorization") String authorization) {
		coreVolunteerService.unsubscribeTenant(coreVolunteerId, marketplaceId, tenantId, authorization);
		return ResponseEntity.ok().build();
	}

}
