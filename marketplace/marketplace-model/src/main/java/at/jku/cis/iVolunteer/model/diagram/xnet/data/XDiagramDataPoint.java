package at.jku.cis.iVolunteer.model.diagram.xnet.data;

public class XDiagramDataPoint extends XDiagramData {
    private float value;

    public XDiagramDataPoint() {
    }

    public XDiagramDataPoint(String name, float value) {
        super(name);
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
