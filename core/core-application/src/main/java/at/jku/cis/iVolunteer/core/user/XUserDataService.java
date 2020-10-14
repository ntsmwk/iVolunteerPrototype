package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer._mappers.xnet.XUserRoleRelevanceMapper;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XUserRole;
import at.jku.cis.iVolunteer.model.user.XUserRoleRelevance;

import at.jku.cis.iVolunteer.model.user.XTenantRole;

@Service
public class XUserDataService {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private XUserRoleRelevanceMapper roleMapper;

    public List<XTenantRole> toTenantRoles(List<TenantSubscription> subscriptions) {
        List<TenantSubscription> subs = subscriptions.stream().filter(s -> (!s.getRole().equals(UserRole.VOLUNTEER)))
                .collect(Collectors.toList());

        List<String> uniqueTenantIds = subs.stream().map(s -> s.getTenantId()).distinct().collect(Collectors.toList());

        List<XTenantRole> tenantRoles = new ArrayList<>();
        uniqueTenantIds.forEach(id -> {

            List<UserRole> roles = subs.stream().filter(s -> s.getTenantId().equals(id)).map(s -> s.getRole())
                    .collect(Collectors.toList());

            List<XUserRoleRelevance> xRolesRelevance = new ArrayList<>();
            roles.stream().map(r -> roleMapper.toTarget(r)).forEach(xRolesRelevance::add);

            xRolesRelevance.stream().sorted(Comparator.comparing(XUserRoleRelevance::getRelevance))
                    .collect(Collectors.toList());

            List<XUserRole> xRoles = new ArrayList<>();
            xRolesRelevance.stream().map(r -> new XUserRole(r.getName(), r.getNameShort())).forEach(xRoles::add);

            XTenantRole t = new XTenantRole();
            t.setTenant(tenantService.getTenantById(id));
            t.setRoles(xRoles);

            tenantRoles.add(t);
        });

        return tenantRoles;

    }

}
