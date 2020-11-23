package at.jku.cis.iVolunteer.model.diagram.xnet.data;

public abstract class XDiagramData {
    private String name;

    public XDiagramData() {
    }

    public XDiagramData(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
