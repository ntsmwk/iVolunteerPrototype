package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XTenantRole;

@Service
public class XUserDataService {

    @Autowired
    private TenantService tenantService;

    public List<XTenantRole> toTenantRoles(List<TenantSubscription> subscriptions) {
        List<TenantSubscription> subs = subscriptions.stream().filter(s -> (!s.getRole().equals(UserRole.VOLUNTEER)))
                .collect(Collectors.toList());

        List<String> uniqueTenantIds = subs.stream().map(s -> s.getTenantId()).distinct().collect(Collectors.toList());

        List<XTenantRole> tenantRoles = new ArrayList<>();
        uniqueTenantIds.forEach(id -> {
            XTenantRole t = new XTenantRole();
            t.setTenant(tenantService.getTenantById(id));
            t.setRoles(subs.stream().filter(s -> s.getTenantId().equals(id)).map(s -> s.getRole())
                    .collect(Collectors.toList()));
            tenantRoles.add(t);
        });

        return tenantRoles;

    }

}
