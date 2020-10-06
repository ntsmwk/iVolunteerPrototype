package at.jku.cis.iVolunteer.model.user;

import java.util.List;

public class TenantRoles {
    private String tenantId;
    private List<UserRole> roles;

    public TenantRoles() {
    }

    public TenantRoles(String tenantId, List<UserRole> roles) {
        this.tenantId = tenantId;
        this.roles = roles;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<UserRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

}
