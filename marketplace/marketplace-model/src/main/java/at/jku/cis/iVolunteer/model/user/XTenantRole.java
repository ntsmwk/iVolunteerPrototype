package at.jku.cis.iVolunteer.model.user;

import java.util.List;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class XTenantRole {
    private XTenant tenant;
    private List<XUserRole> roles;

    public XTenantRole() {
    }

    public XTenantRole(XTenant tenant, List<XUserRole> roles) {
        this.tenant = tenant;
        this.roles = roles;
    }

    public List<XUserRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<XUserRole> roles) {
        this.roles = roles;
    }

	public XTenant getTenant() {
		return tenant;
	}

	public void setTenant(XTenant tenant) {
		this.tenant = tenant;
	}

}
