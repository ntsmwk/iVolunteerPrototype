package at.jku.cis.iVolunteer.model.user;

import java.util.List;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

public class XTenantRole {
    private Tenant tenant;
    private List<XUserRole> roles;

    public XTenantRole() {
    }

    public XTenantRole(Tenant tenant, List<XUserRole> roles) {
        this.tenant = tenant;
        this.roles = roles;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<XUserRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<XUserRole> roles) {
        this.roles = roles;
    }

}
