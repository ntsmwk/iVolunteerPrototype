package at.jku.cis.iVolunteer.model;

import at.jku.cis.iVolunteer.model.user.UserRole;

public class TenantUserSubscription {
    private String tenantId;
    private UserRole role;

    public TenantUserSubscription(String tenantId, UserRole role) {
        this.tenantId = tenantId;
        this.role = role;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}