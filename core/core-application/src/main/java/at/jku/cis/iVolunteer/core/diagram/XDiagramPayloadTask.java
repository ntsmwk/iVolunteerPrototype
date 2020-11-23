package at.jku.cis.iVolunteer.core.diagram;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramDisplayTask;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramFilterTask;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramOrder;

public class XDiagramPayloadTask {
    private XDiagramFilterTask filter;
    private XDiagramOrder order;
    private XDiagramDisplayTask display;

    public XDiagramPayloadTask() {
    }

    public XDiagramFilterTask getFilter() {
        return this.filter;
    }

    public void setFilter(XDiagramFilterTask filter) {
        this.filter = filter;
    }

    public XDiagramOrder getOrder() {
        return this.order;
    }

    public void setOrder(XDiagramOrder order) {
        this.order = order;
    }

    public XDiagramDisplayTask getDisplay() {
        return this.display;
    }

    public void setDisplay(XDiagramDisplayTask display) {
        this.display = display;
    }

}
