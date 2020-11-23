package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.ArrayList;
import java.util.List;

public class XDiagramDataArray extends XDiagramData {
    private List<XDiagramData> children;

    public XDiagramDataArray() {
    }

    public XDiagramDataArray(String name) {
        super(name);
        this.children = new ArrayList<>();
    }

    public XDiagramDataArray(String name, List<XDiagramData> children) {
        super(name);
        this.children = children;
    }

    public List<XDiagramData> getChildren() {
        return this.children;
    }

    public void setChildren(List<XDiagramData> children) {
        this.children = children;
    }
}
