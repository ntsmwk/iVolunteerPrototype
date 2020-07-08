package at.jku.cis.iVolunteer.model;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

public class TenantUserSubscription {
    private Marketplace marketplace;
    private Tenant tenant;
    private UserRole role;

    public TenantUserSubscription() {
    }

    public TenantUserSubscription(Marketplace marketplace, Tenant tenant, UserRole role) {
        this.marketplace = marketplace;
        this.tenant = tenant;
        this.role = role;
    }

    public Marketplace getMarketplace() {
        return this.marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}