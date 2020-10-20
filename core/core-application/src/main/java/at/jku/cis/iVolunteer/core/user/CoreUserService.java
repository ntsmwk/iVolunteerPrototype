package at.jku.cis.iVolunteer.core.user;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotFoundException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreUserService {

	@Autowired private CoreUserRepository coreUserRepository;
	@Autowired private TenantRepository tenantRepository;
	@Autowired private CoreMarketplaceRestClient coreMarketplaceRestClient;
	@Autowired private MarketplaceService marketplaceService;

	public List<CoreUser> findAll() {
		return coreUserRepository.findAll();
	}
	
	public CoreUser findById(String id) {
		return coreUserRepository.findOne(id);
	}
	
	public List<CoreUser> findByIds(List<String> ids) {
		List<CoreUser> users = new LinkedList<>();
		coreUserRepository.findAll(ids).forEach(users::add);
		return users;
	}

	public List<CoreUser> getAllByTenantId(String tenantId) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();

		if (allUsers == null) {
			return returnUsers;
		}

		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream().filter(st -> st.getTenantId().equals(tenantId)).findFirst()
					.isPresent()) {
				returnUsers.add(user);
			}
		}
		return returnUsers;
	}

	public List<CoreUser> getAllByUserRole(UserRole userRole) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();

		if (allUsers == null) {
			return returnUsers;
		}

		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream().filter(st -> st.getRole().equals(userRole)).findFirst()
					.isPresent()) {
				returnUsers.add(user);
			}
		}
		return returnUsers;
	}

	public List<CoreUser> getAllByUserRoles(List<UserRole> roles, boolean includeNoRole) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();

		if (allUsers == null) {
			return returnUsers;
		}

		for (CoreUser user : allUsers) {
			if ((includeNoRole && user.getSubscribedTenants().size() == 0) || user.getSubscribedTenants().stream()
					.filter(st -> roles.contains(st.getRole())).findFirst().isPresent()) {
				returnUsers.add(user);
			}
		}

		return returnUsers;
	}

	public List<CoreUser> getAllByTenantIdAndUserRole(UserRole userRole, String tenantId) {
		List<CoreUser> returnUsers = new ArrayList<>();
		List<CoreUser> allUsers = coreUserRepository.findAll();

		for (CoreUser user : allUsers) {
			if (user.getSubscribedTenants().stream()
					.filter(st -> st.getTenantId().equals(tenantId) && st.getRole().equals(userRole)).findFirst()
					.isPresent()) {
				returnUsers.add(user);
			}
		}

		return returnUsers;
	}

	public CoreUser getByUserId(String userId) {
		return coreUserRepository.findOne(userId);
	}

	public CoreUser getByUserName(String username) {
		return coreUserRepository.findByUsername(username);
	}

	public List<CoreUser> getByUserId(List<String> userIds) {
		List<CoreUser> users = new ArrayList<>();
		coreUserRepository.findAll(userIds).forEach(users::add);
		return users;
	}

	public List<Marketplace> findRegisteredMarketplaces(String userId) {
		CoreUser user = coreUserRepository.findOne(userId);
		if (user == null) {
			return null;
		}
		List<Marketplace> marketplaces = marketplaceService.findAll(user.getRegisteredMarketplaceIds());
		return marketplaces;
	}

	public Marketplace getOnlyFirstMarketplace(String userId) {
		CoreUser user = coreUserRepository.findOne(userId);
		if (user.getRegisteredMarketplaceIds().isEmpty()) {
			return null;
		}
		return this.findRegisteredMarketplaces(userId).get(0);
	}

	// TODO xnet
	/**
	 * Core - Marketplace Kommunikation sync von usern core / marketplace - Wie soll
	 * gehandelt werden, wenn marketplace nicht erreichbar ist?
	 * 
	 * retry? rollback? automatischer sync bei jedem zugriff auf marketplace?
	 */

	public CoreUser registerToMarketplace(String userId, String marketplaceId, String authorization) {
		CoreUser coreUser = coreUserRepository.findOne(userId);
		Marketplace marketplace = marketplaceService.findById(marketplaceId);

		if (coreUser == null || marketplace == null) {
			throw new NotFoundException();
		}

		coreUser.getRegisteredMarketplaceIds().add(marketplaceId);
		coreUser = coreUserRepository.save(coreUser);

		User marketplaceUser = new User(coreUser);
		coreMarketplaceRestClient.registerOrUpdateMarketplaceUser(marketplace.getUrl(), authorization, marketplaceUser);

		return coreUser;
	}

	public CoreUser addNewUser(CoreUser user, String authorization, boolean updateMarketplaces) {
		if (user.getId() == null) {
			user.setId(new ObjectId().toHexString());
		}
		return this.updateUser(user, authorization, updateMarketplaces);
	}

	public CoreUser updateUser(CoreUser user, String authorization, boolean updateMarketplaces) {
		CoreUser existingUser = coreUserRepository.findOne(user.getId());
		if (existingUser != null) {
			user.setPassword(existingUser.getPassword());
		}
		user = this.coreUserRepository.save(user);
		if (updateMarketplaces) {
			this.updateMarketplaces(user, authorization);
		}

		user.setPassword(null);
		return user;
	}

	private void updateMarketplaces(CoreUser coreUser, String authorization) {
		List<Marketplace> marketplaces = this.findRegisteredMarketplaces(coreUser.getId());

		User user = new User(coreUser);

		for (Marketplace marketplace : marketplaces) {
			coreMarketplaceRestClient.registerOrUpdateMarketplaceUser(marketplace.getUrl(), authorization, user);
		}

	}

	public CoreUser subscribeUserToTenant(String userId, String marketplaceId, String tenantId, UserRole role,
			String authorization, boolean updateMarketplace) {
		CoreUser user = coreUserRepository.findOne(userId);
		Marketplace marketplace = marketplaceService.findById(marketplaceId);

		if (marketplace == null || user == null || !tenantRepository.exists(tenantId)) {
			return null;
		}

		user.addSubscribedTenant(marketplaceId, tenantId, role);
		user = coreUserRepository.save(user);

		if (updateMarketplace) {
			coreMarketplaceRestClient.subscribeUserToTenant(marketplace.getUrl(), marketplaceId, tenantId, userId,
					authorization, role);
		}

		return user;
	}

	public CoreUser unsubscribeUserFromTenant(String userId, String marketplaceId, String tenantId, UserRole role,
			String authorization, boolean updateMarketplace) {
		CoreUser user = coreUserRepository.findOne(userId);
		Marketplace marketplace = marketplaceService.findById(marketplaceId);

		if (marketplace == null || user == null || !tenantRepository.exists(tenantId)) {
			return null;
		}

		user.removeSubscribedTenant(marketplaceId, tenantId, role);
		user = coreUserRepository.save(user);

		if (updateMarketplace) {
			coreMarketplaceRestClient.unsubscribeUserFromTenant(marketplace.getUrl(), marketplaceId, tenantId, userId,
					authorization, role);
		}

		return user;
	}

	// -----

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