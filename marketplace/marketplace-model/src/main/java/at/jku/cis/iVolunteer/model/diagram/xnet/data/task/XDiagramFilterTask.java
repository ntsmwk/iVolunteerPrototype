package at.jku.cis.iVolunteer.model.diagram.xnet.data.task;

import java.util.Date;
import java.util.List;

public class XDiagramFilterTask {
    private Date start;
    private Date end;
    private List<String> tenantIds;

    public XDiagramFilterTask() {
    }

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<String> getTenantIds() {
        return this.tenantIds;
    }

    public void setTenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
    }

}
