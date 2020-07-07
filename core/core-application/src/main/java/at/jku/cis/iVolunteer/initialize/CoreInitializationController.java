package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;

@RestController
public class CoreInitializationController {

	@Autowired private CoreInitializationService coreInitializationService;
	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;
	@Autowired private CoreTenantInitializationService coreTenantInitializationService;
	
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
	 * User Registration - tenant
	 */
	
	@PutMapping("/init/register-users")
	public void registerUsers() {
		registerVolunteers();
		registerHelpSeekers();
		registerAdmins();
		registerRecruiters();
//		registerFlexProdUsers();
	}
	
	@PutMapping("/init/register-volunteers")
	public void registerVolunteers() {
		coreVolunteerInitializationService.registerVolunteers();
	}
	
	@PutMapping("init/register-helpseekers")
	public void registerHelpSeekers() {
		coreHelpSeekerInitializationService.registerDefaultHelpSeekers();
	}
	
	@PutMapping("/init/register-admins") 
	public void registerAdmins() {
		
	}
	
	@PutMapping("/init/register-recruiters") 
	public void registerRecruiters() {
		
	}
	
	@PutMapping("/init/register-flexprods") 
	public void registerFlexProdUsers() {
		
	}
	
	/**
	 * Tenant Subscription
	 */
	
	@PutMapping("/init/tenant/subscribe/all")
	public void subscribeAllToTenant() {
		
	}
	
	@PutMapping("/init/tenant/subscribe/helpseekers")
	public void subscribeHelpseekersToTenant() {
		
	}
	
	@PutMapping("/init/tenant/subscribe/volunteers")
	public void subscribeVolunteersToTenant() {
		
	}
	
	@PutMapping("/init/tenant/unsubscribe/all")
	public void unsubscribeAllFromTenant() {
		
	}
	
	@PutMapping("/init/tenant/unsubscribe/helpseekers")
	public void unsubscribeHelpseekersFromTenant() {
		
	}
	
	@PutMapping("/init/tenant/unsubscribe/volunteers")
	public void unsubscribeVolunteersFromTenant() {
		
	}
	
	@PutMapping("init/wipe-core") 
	public void wipeCore() {
		
	}
	
}