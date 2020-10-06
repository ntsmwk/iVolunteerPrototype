package at.jku.cis.iVolunteer.core.global;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

public class UserSubscriptionDTO {
    private Marketplace marketplace;
    private Tenant tenant;
    private UserRole role;

    public UserSubscriptionDTO() {
    }

    public UserSubscriptionDTO(Marketplace marketplace, Tenant tenant, UserRole role) {
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
