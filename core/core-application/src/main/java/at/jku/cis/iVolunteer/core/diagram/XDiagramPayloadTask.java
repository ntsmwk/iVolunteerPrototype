package at.jku.cis.iVolunteer.core.diagram;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramDisplayTask;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramFilterTask;

public class XDiagramPayloadTask {
    private XDiagramFilterTask filter;
    private XDiagramDisplayTask display;
    private boolean orderAsc = true;

    public XDiagramPayloadTask() {
    }

    public XDiagramFilterTask getFilter() {
        return this.filter;
    }

    public void setFilter(XDiagramFilterTask filter) {
        this.filter = filter;
    }

    public XDiagramDisplayTask getDisplay() {
        return this.display;
    }

    public void setDisplay(XDiagramDisplayTask display) {
        this.display = display;
    }

    public boolean isOrderAsc() {
        return this.orderAsc;
    }

    public boolean getOrderAsc() {
        return this.orderAsc;
    }

    public void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

}
