package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.admin.CoreAdminController;
import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerService;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterController;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;

@RestController
public class CoreInitializationController {

	@Autowired private CoreInitializationService coreInitializationService;
	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;
	@Autowired private CoreTenantInitializationService coreTenantInitializationService;
	
	@Autowired private CoreHelpSeekerService coreHelpSeekerService;
	@Autowired private CoreVolunteerService coreVolunteerService;
	@Autowired private CoreAdminController coreAdminController;
	@Autowired private CoreRecruiterController coreRecruiterController;
	
	/**
	 * Marketplace / Tenants
	 */
	
	@PutMapping("/init/create-marketplace") 
	public void createMarketplace() {
		coreInitializationService.createMarketplace();
	}
	
	@DeleteMapping("/init/delete-marketplace")
	public void deleteMarketplace() {
		
	}
	
	@PutMapping("/init/create-tenants") 
	public void createTenants() {
		coreTenantInitializationService.initTenants();
	}
	
	@DeleteMapping("/init/delete-tenants")
	public void deleteTenants() {
		coreTenantInitializationService.coreTenantRepository.deleteAll();
	}
	

	/**
	 * User creation
	 */
	
	@PutMapping("/init/create-users")
	public void createUsers() {
		createVolunteers();
		createHelpseekers();
		createAdmins();
		createRecruiters();
//		createFlexProdUsers();
	}
	
	@DeleteMapping("/init/delete-users")
	public void deleteUsers() {
		coreInitializationService.coreUserRepository.deleteAll();
	}
	
	@PutMapping("/init/create-volunteers") 
	public void createVolunteers(){
		coreVolunteerInitializationService.initVolunteers();
	}
	
	@PutMapping("/init/create-helpseekers") 
	public void createHelpseekers() {
		coreHelpSeekerInitializationService.initHelpSeekers();
	}

	@PutMapping("/init/create-admins") 
	public void createAdmins() {
		coreInitializationService.createStandardAdminUser();
	}
	
	@PutMapping("/init/create-recruiters") 
	public void createRecruiters() {
		coreInitializationService.createStandardRecruiter();
	}
	
	@PutMapping("/init/create-flexprods") 
	public void createFlexProdUsers() {
		coreInitializationService.createStandardFlexProdUser();
	}
	
	/**
	 * Tenant Subscription
	 */
	
	@PutMapping("/init/tenant/subscribe/all")
	public void subscribeAllToTenant() {
		subscribeHelpseekersToTenant();
		subscribeVolunteersToTenant();
		subscribeAdminsToTenant();
		subscribeRecruitersToTenant();
	}
	
	@PutMapping("/init/tenant/subscribe/helpseekers")
	public void subscribeHelpseekersToTenant() {
		coreHelpSeekerInitializationService.subscribeDefaultHelpseekersToTenants();
	}
	
	@PutMapping("/init/tenant/subscribe/volunteers")
	public void subscribeVolunteersToTenant() {
		coreVolunteerInitializationService.subscribeVolunteersToAllTenants();
	}
	
	@PutMapping("/init/tenant/subscribe/admins")
	public void subscribeAdminsToTenant() {
		
		//TODO
	}
	
	@PutMapping("/init/tenant/subscribe/recruiters")
	public void subscribeRecruitersToTenant() {
		
		//TODO
	}
	
	@PutMapping("/init/tenant/unsubscribe/all")
	public void unsubscribeAllFromTenant() {
		//TODO
	}
	
	@PutMapping("/init/tenant/unsubscribe/helpseekers")
	public void unsubscribeHelpseekersFromTenant() {
		//TODO
	}
	
	
	@PutMapping("/init/tenant/unsubscribe/volunteers")
	public void unsubscribeVolunteersFromTenant() {
		//TODO
	}
	
	
	/**
	 * Marketplace registration
	 */
	
	@PutMapping("/init/marketplace/register/all")
	public void registerAllToMarketplace() {
		registerHelpseekersToMarketplace();
		registerVolunteersToMarketplace();
		registerAdminsToMarketplace();
		registerRecruitersToMarketplace();
		
	}
	
	@PutMapping("/init/marketplace/register/helpseekers")
	public void registerHelpseekersToMarketplace() {
		coreHelpSeekerInitializationService.registerDefaultHelpSeekers();
	}
	
	@PutMapping("/init/marketplace/register/volunteers")
	public void registerVolunteersToMarketplace() {
		coreVolunteerInitializationService.registerVolunteers();	
	}
	
	@PutMapping("/init/marketplace/register/admins")
	public void registerAdminsToMarketplace() {
		
		//TODO
	}
	
	@PutMapping("/init/marketplace/register/recruiters")
	public void registerRecruitersToMarketplace() {
		
		//TODO
	}
	
	@PutMapping("/init/marketplace/unregister/all")
	public void unregisterAllFromMarketplace() {
		//TODO
	}
	
	@PutMapping("/init/marketplace/unregister/helpseekers")
	public void unregisterHelpseekersFromMarketplace() {
		//TODO
	}
	
	
	@PutMapping("/init/marketplace/unregister/volunteers")
	public void unregistereVolunteersFromMarketplace() {
		//TODO
	}
	
	
	@PutMapping("init/wipe-core") 
	public void wipeCore() {
		
	}
	
}