package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer._mappers.xnet.XTenantMapper;
import at.jku.cis.iVolunteer._mappers.xnet.XUserRoleMapper;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;

import at.jku.cis.iVolunteer.model.user.XTenantRole;

@Service
public class XUserDataService {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private XUserRoleMapper roleMapper;
    
    @Autowired XTenantMapper tenantMapper;

    public List<XTenantRole> toXTenantRoles(List<TenantSubscription> subscriptions) {
        List<TenantSubscription> subs = subscriptions.stream().filter(s -> (!s.getRole().equals(UserRole.VOLUNTEER)))
                .collect(Collectors.toList());

        List<String> uniqueTenantIds = subs.stream().map(s -> s.getTenantId()).distinct().collect(Collectors.toList());

        List<XTenantRole> tenantRoles = new ArrayList<>();

        uniqueTenantIds.forEach(id -> {
            List<UserRole> roles = subs.stream().filter(s -> s.getTenantId().equals(id)).map(s -> s.getRole())
                    .collect(Collectors.toList());

            XTenantRole t = new XTenantRole();
            
			List<CoreUser> users = tenantService.getSubscribedUsers(id);
            t.setTenant(tenantMapper.toTarget(tenantService.getTenantById(id), users));
            t.setRoles(roleMapper.toTargets(roles));

            tenantRoles.add(t);
        });

        return tenantRoles;

    }

}
