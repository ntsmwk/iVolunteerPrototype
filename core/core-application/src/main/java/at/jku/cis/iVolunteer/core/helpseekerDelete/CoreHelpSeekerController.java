package at.jku.cis.iVolunteer.core.helpseekerDelete;
//package at.jku.cis.iVolunteer.core.helpseeker;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
//import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
//import at.jku.cis.iVolunteer.core.user.CoreUserService;
//import at.jku.cis.iVolunteer.model.core.user.CoreUser;
//import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
//import at.jku.cis.iVolunteer.model.user.UserRole;
//
//@RestController
//@RequestMapping("/helpseeker")
//public class CoreHelpSeekerController {
//
//	@Autowired
//	private CoreUserRepository coreUserRepository;
//	@Autowired
//	private MarketplaceRepository marketplaceRepository;
//	@Autowired
//	private CoreHelpSeekerService coreHelpSeekerService;
//	@Autowired
//	private CoreUserService coreUserService;
//
//	@GetMapping("/all")
//	public List<CoreUser> getAllCoreHelpSeekers(@RequestParam(value = "tId", required = false) String tenantId) {
//		return coreUserService.getCoreUsersByRoleAndSubscribedTenants(UserRole.HELP_SEEKER, tenantId);
//
//	}
//
//	@PutMapping("/find-by-ids")
//	public List<CoreUser> getAllCoreVolunteers(@RequestBody List<String> coreHelpseekerIds) {
//		List<CoreUser> coreHelpseekers = new ArrayList<>();
//
//		this.coreUserService.getCoreUsersByRoleAndId(UserRole.HELP_SEEKER, coreHelpseekerIds)
//				.forEach(coreHelpseekers::add);
//
//		return coreHelpseekers;
//	}
//
//	@GetMapping("/{coreHelpSeekerId}")
//	public CoreUser getCorehelpSeeker(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
//		return coreUserRepository.findOne(coreHelpSeekerId);
//	}
//
//	@GetMapping("/{coreHelpSeekerId}/marketplace")
//	public Marketplace getRegisteredMarketplaces(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId) {
//		CoreUser helpSeeker = coreUserRepository.findOne(coreHelpSeekerId);
//		if (helpSeeker.getRegisteredMarketplaceIds().isEmpty()) {
//			return null;
//		}
//		return this.marketplaceRepository.findOne(helpSeeker.getRegisteredMarketplaceIds().get(0));
//	}
//
//	@PostMapping("/{coreHelpSeekerId}/register/{marketplaceId}/tenant/{tenantId}")
//	public void registerMarketpace(@PathVariable("coreHelpSeekerId") String coreHelpSeekerId,
//			@PathVariable("marketplaceId") String marketplaceId, @PathVariable("tenantId") String tenantId,
//			@RequestHeader("Authorization") String authorization) {
//
//		coreHelpSeekerService.registerMarketplace(coreHelpSeekerId, marketplaceId, tenantId, authorization);
//
//	}
//
//}