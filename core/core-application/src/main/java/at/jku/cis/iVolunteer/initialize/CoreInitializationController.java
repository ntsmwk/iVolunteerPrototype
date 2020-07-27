package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CoreInitializationController {

	@Autowired private CoreInitializationService coreInitializationService;
	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;
	@Autowired private CoreTenantInitializationService coreTenantInitializationService;
	
	
	@PutMapping("/init/stage-one")
	public void createMarketplaceTenantsAndUsers() {
		createMarketplace();
		createTenants();
		createUsers();
        registerAllToMarketplace();
	}
	
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
	
	@PutMapping("/init/tenant/subscribe/all-except-volunteers")
	public void subscribeAllToTenantExceptVolunteers() {
		subscribeHelpseekersToTenant();
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
		coreInitializationService.subscribeAdminsToTenant();
	}
	
	@PutMapping("/init/tenant/subscribe/recruiters")
	public void subscribeRecruitersToTenant() {
		coreInitializationService.subscribedRecruitersToTenant();
	}
	
	@PutMapping("/init/tenant/unsubscribe/all")
	public void unsubscribeAllFromTenant() {
	}
	
	@PutMapping("/init/tenant/unsubscribe/helpseekers")
	public void unsubscribeHelpseekersFromTenant() {
		//TODO init
	}
	
	
	@PutMapping("/init/tenant/unsubscribe/volunteers")
	public void unsubscribeVolunteersFromTenant() {
		//TODO init
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
		coreInitializationService.registerAdminsToMarketplace();
	}
	
	@PutMapping("/init/marketplace/register/recruiters")
	public void registerRecruitersToMarketplace() {
		coreInitializationService.registerRecruitersToMarketplace();
	}
	
	@PutMapping("/init/marketplace/unregister/all")
	public void unregisterAllFromMarketplace() {
		//TODO init
	}
	
	@PutMapping("/init/marketplace/unregister/helpseekers")
	public void unregisterHelpseekersFromMarketplace() {
		//TODO init
	}
	
	
	@PutMapping("/init/marketplace/unregister/volunteers")
	public void unregistereVolunteersFromMarketplace() {
		//TODO init
	}
	
	
	@PutMapping("init/wipe-core") 
	public void wipeCore() {
		coreInitializationService.coreUserRepository.deleteAll();
		coreTenantInitializationService.coreTenantRepository.deleteAll();
		
	}
	
}