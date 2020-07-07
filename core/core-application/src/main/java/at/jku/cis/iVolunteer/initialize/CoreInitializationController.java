package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreInitializationController {

	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;
	
	/**
	 * Marketplace / Tenants
	 */
	
	@PutMapping("/init/create-marketplace") 
	public void createMarketplace() {
		
	}
	
	@DeleteMapping("/init/delete-marketplace")
	public void deleteMarketplace() {
		
	}
	
	@PutMapping("/init/create-tenants") 
	public void createTenants() {
		
	}
	
	@DeleteMapping("/init/delete-tenants")
	public void deleteTenants() {
		
	}
	

	/**
	 * User creation
	 */
	
	@PutMapping("/init/create-users")
	public void createUsers() {
		
	}
	
	@DeleteMapping("/init/delete-users")
	public void deleteUsers() {
		
	}
	
	@PutMapping("/init/create-volunteers") 
	public void createVolunteers(){
		
	}
	
	@PutMapping("/init/create-helpseekers") 
	public void createHelpseekers() {
		
	}
	
	@PutMapping("/init/create-admins") 
	public void createAdmins() {
		
	}
	
	@PutMapping("/init/create-recruiters") 
	public void createRecruiters() {
		
	}
	
	@PutMapping("/init/create-flexprods") 
	public void createFlexProdUsers() {
		
	}
	
	/**
	 * User Registration
	 */
	
	@PutMapping("/init/register-users")
	public void registerUsers() {
		
	}
	
	/**
	 * Marketplace Registration
	 */

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
	
}