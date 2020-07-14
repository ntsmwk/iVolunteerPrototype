package at.jku.cis.iVolunteer.marketplace.user;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

import javax.ws.rs.BadRequestException;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user/{id}")
    private User findUserById(@PathVariable("id") String id) {
    	return userRepository.findOne(id);
    }

    @GetMapping("/user/all/role/{role}")
    private List<User> findAllByRole(@PathVariable("role") UserRole role) {
    	//TODO implement
    	return null;
    }

    @GetMapping("/user/all/tenant/{tenantId}")
    private List<User> findAllByTenantId(@PathVariable("role") UserRole role) {
    	//TODO implement
    	return null;
    }

    @GetMapping("/user/all/tenant/{tenantId}/role/{role}")
    private List<User> findAllByRoleAndTenantId(@PathVariable("tenantId") String tenantId, @PathVariable("role") UserRole role) {
    	//TODO implement
    	return null;
    }
    
    @GetMapping("/user/username/{username}")
    private User findUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    
    @PostMapping("/user/register")
    public User registerUserToMarketplace(@RequestBody User user) {
    	return userRepository.save(user);
    }
    
    @PostMapping("/user/subscribe/{marketplaceId}/{tenantId}/{userId}/{role}")
    private User subscribeUserToTenant(@PathVariable("marketplaceId") String marketplaceId, @PathVariable("userId") String userId, @PathVariable("tenantId") String tenantId, @PathVariable("role") UserRole role) {
    	User user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		return null;
    	}
    	
		user.addSubscribedTenant(marketplaceId, tenantId, role);
		user = userRepository.save(user);
    	
    	return user;
    }
    
    private TenantUserSubscription findTenantUserSubscription(User user, String marketplaceId, String tenantId, UserRole role) {
    	return user.getSubscribedTenants().stream().filter(st -> st.getMarketplaceId().equals(marketplaceId) && st.getTenantId().equals(tenantId) && st.getRole().equals(role)).findFirst().get();
    }
    
    
    
    @PostMapping("/user/unsubscribe/{marketplaceId}/{tenantId}/{userId}/{role}")
    private User unsubscribeUserToTenant(@PathVariable("marketplaceId") String marketplaceId, @PathVariable("userId") String userId, @PathVariable("tenantId") String tenantId, @PathVariable("role") UserRole role) {
    	User user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		return null;
    	}
    	
    	
    	user.removeSubscribedTenant(marketplaceId, tenantId, role);
    	user = userRepository.save(user);
    	
    	return user;
    }


}