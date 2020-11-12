package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.Date;

public class XDiagramReturnEntity {
    private Date refreshTimestamp;
    private XDiagramData data;

    public XDiagramReturnEntity(Date refreshTimestamp, XDiagramData data) {
        this.refreshTimestamp = refreshTimestamp;
        this.data = data;
    }

    public Date getRefreshTimestamp() {
        return this.refreshTimestamp;
    }

    public void setRefreshTimestamp(Date refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

    public XDiagramData getData() {
        return this.data;
    }

    public void setData(XDiagramData data) {
        this.data = data;
    }

}
