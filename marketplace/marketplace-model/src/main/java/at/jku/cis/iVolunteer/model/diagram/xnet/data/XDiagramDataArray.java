package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.List;

public class XDiagramDataArray extends XDiagramData {
    private List<XDiagramDataPoint> children;

    public XDiagramDataArray() {
    }

    public XDiagramDataArray(String name, List<XDiagramDataPoint> children) {
        super(name);
        this.children = children;
    }

    public List<XDiagramDataPoint> getChildren() {
        return this.children;
    }

    public void setChildren(List<XDiagramDataPoint> children) {
        this.children = children;
    }

}
