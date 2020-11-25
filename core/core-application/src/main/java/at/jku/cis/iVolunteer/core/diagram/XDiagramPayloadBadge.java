package at.jku.cis.iVolunteer.core.diagram;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramDisplayBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramFilterBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramOrderBadge;

public class XDiagramPayloadBadge {
    private XDiagramOrderBadge order;
    private XDiagramFilterBadge filter;
    private XDiagramDisplayBadge display;

    public XDiagramPayloadBadge() {
    }

    public XDiagramOrderBadge getOrder() {
        return this.order;
    }

    public void setOrder(XDiagramOrderBadge order) {
        this.order = order;
    }

    public XDiagramFilterBadge getFilter() {
        return this.filter;
    }

    public void setFilter(XDiagramFilterBadge filter) {
        this.filter = filter;
    }

    public XDiagramDisplayBadge getDisplay() {
        return this.display;
    }

    public void setDisplay(XDiagramDisplayBadge display) {
        this.display = display;
    }

}
