package at.jku.cis.iVolunteer.model.user;

public class TenantSubscription {
    private String marketplaceId;
    private String tenantId;
    private UserRole role;

    public TenantSubscription() {
    }

    public TenantSubscription(String marketplaceId, String tenantId, UserRole role) {
        this.marketplaceId = marketplaceId;
        this.tenantId = tenantId;
        this.role = role;
    }

    public String getMarketplaceId() {
        return this.marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
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