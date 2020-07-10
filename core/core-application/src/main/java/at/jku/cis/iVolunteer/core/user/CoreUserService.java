package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.model.TenantUserSubscription;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreUserService {

    @Autowired private CoreUserRepository coreUserRepository;
    @Autowired private MarketplaceRepository marketplaceRepository;
    @Autowired private TenantRepository tenantRepository;
    @Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;

	List<CoreUser> findAll() {
		return coreUserRepository.findAll();
	}

	List<CoreUser> getAllByTenantId(String tenantId) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();
		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream().filter(st -> st.getTenantId().equals(tenantId)).findFirst().isPresent()) {
				returnUsers.add(user);
			}
		}
		return returnUsers;
	}

	List<CoreUser> getAllByUserRole(UserRole userRole) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();
		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream().filter(st -> st.getRole().equals(userRole)).findFirst().isPresent()) {
				returnUsers.add(user);
			}
		}
		return returnUsers;
	}

	List<CoreUser> getAllByTenantIdAndUserRole(UserRole userRole, String tenantId) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();
		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream().filter(st -> st.getTenantId().equals(tenantId) && st.getRole().equals(userRole)).findFirst().isPresent()) {
				returnUsers.add(user);
			}
		}
		return returnUsers;
	}

	CoreUser getByUserId(String userId) {
		return coreUserRepository.findOne(userId);
	}

	CoreUser getByUserName(String username) {
		return coreUserRepository.findByUsername(username);
	}

	List<CoreUser> getByUserId(List<String> userIds) {
		List<CoreUser> users = new ArrayList<>();
		coreUserRepository.findAll(userIds).forEach(users::add);
		return users;
	}

	List<Marketplace> findRegisteredMarketplaces(String userId) {
		CoreUser user = coreUserRepository.findOne(userId);
		if (user == null) {
			return null;
		}
		
		List<Marketplace> marketplaces = new ArrayList<>();
		marketplaceRepository.findAll(user.getRegisteredMarketplaceIds()).forEach(marketplaces::add);;
		return marketplaces;
	}

	CoreUser registerToMarketplace(String userId, String marketplaceId, String authorization) {
		CoreUser coreUser = coreUserRepository.findOne(userId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);

		if (coreUser == null || marketplace == null) {
			throw new NotFoundException();
		}
		
		coreUser.getRegisteredMarketplaceIds().add(marketplaceId);
		coreUser = coreUserRepository.save(coreUser);
		
		User marketplaceUser = new User(coreUser);
		coreMarketplaceRestClient.registerUser(marketplace.getUrl(), authorization, marketplaceUser);
		
		return coreUser;
	}
	

	CoreUser addNewUser(CoreUser user) {
		return this.coreUserRepository.save(user);
	}
	
	CoreUser updateUser(CoreUser user) {
		return this.coreUserRepository.save(user);
	}

	CoreUser subscribeUserToTenant(String userId, String marketplaceId,  String tenantId, UserRole role) {
		//TODO AK
		CoreUser user = coreUserRepository.findOne(userId);
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		
		if (marketplace == null || user == null || !tenantRepository.exists(tenantId)) {
			return null;
		}
		
		return null;
	}

	CoreUser unsubscribeUserFromTenant(String userId, String marketplaceId, String tenantId, UserRole role) {
		//TODO AK
		return null;
	}
    
    
    
    //-----
    
    
    

    public List<CoreUser> getCoreUsersByRole(UserRole role) {
        List<CoreUser> allUsers = this.coreUserRepository.findAll();

        List<CoreUser> users = new ArrayList<>();
        users.addAll(allUsers
                .stream().filter(u -> u.getSubscribedTenants().size() > 0).filter(u -> u.getSubscribedTenants().stream()
                        .map(t -> t.getRole()).collect(Collectors.toList()).contains(role))
                .collect(Collectors.toList()));

        if (role == UserRole.VOLUNTEER) {
            users.addAll(
                    allUsers.stream().filter(u -> u.getSubscribedTenants().size() == 0).collect(Collectors.toList()));
        }

        return users;
    }

    public List<CoreUser> getCoreUsersByRoleAndId(UserRole role, List<String> userIds) {
        List<CoreUser> users = getCoreUsersByRole(role);

        return users.stream().filter(u -> userIds.contains(u.getId())).collect(Collectors.toList());
    }

    public List<CoreUser> getCoreUsersByRoleAndSubscribedTenants(UserRole role, String tenantId) {
        return getCoreUsersByRole(role)
                .stream().filter(u -> u.getSubscribedTenants().size() > 0).filter(u -> u.getSubscribedTenants().stream()
                        .map(t -> t.getTenantId()).collect(Collectors.toList()).contains(tenantId))
                .collect(Collectors.toList());

    }

}