package at.jku.cis.iVolunteer.model.diagram.xnet.data.badge;

import java.util.List;

public class XDiagramFilterBadge {
    private List<Integer> years;
    private List<String> tenantIds;

    public XDiagramFilterBadge() {
    }

    public List<Integer> getYears() {
        return this.years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<String> getTenantIds() {
        return this.tenantIds;
    }

    public void setTenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
    }

}
