package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.Date;
import java.util.List;

public class XDiagramReturnEntity {
    private Date refreshTimestamp;
    private List<XDiagramData> data;

    public XDiagramReturnEntity() {

    }

    public XDiagramReturnEntity(Date refreshTimestamp, List<XDiagramData> data) {
        this.refreshTimestamp = refreshTimestamp;
        this.data = data;
    }

    public Date getRefreshTimestamp() {
        return this.refreshTimestamp;
    }

    public void setRefreshTimestamp(Date refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

    public List<XDiagramData> getData() {
        return this.data;
    }

    public void setData(List<XDiagramData> data) {
        this.data = data;
    }

    public void addData(XDiagramData datapoint) {
        this.data.add(datapoint);
    }

}
