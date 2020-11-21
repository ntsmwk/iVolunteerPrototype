package at.jku.cis.iVolunteer.core.diagram;

import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramOrder;

public class XDiagramPayload {
    private XDiagramFilter filter;
    private XDiagramOrder order;
    private XDiagramDisplay display;

    public XDiagramPayload() {
    }

    public XDiagramFilter getFilter() {
        return this.filter;
    }

    public void setFilter(XDiagramFilter filter) {
        this.filter = filter;
    }

    public XDiagramOrder getOrder() {
        return this.order;
    }

    public void setOrder(XDiagramOrder order) {
        this.order = order;
    }

    public XDiagramDisplay getDisplay() {
        return this.display;
    }

    public void setDisplay(XDiagramDisplay display) {
        this.display = display;
    }

}
