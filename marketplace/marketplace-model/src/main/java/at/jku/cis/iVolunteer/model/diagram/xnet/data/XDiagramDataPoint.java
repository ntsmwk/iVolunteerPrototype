package at.jku.cis.iVolunteer.model.diagram.xnet.data;

public class XDiagramDataPoint extends XDiagramData {
    private double value;

    public XDiagramDataPoint() {
    }

    public XDiagramDataPoint(String name, double value) {
        super(name);
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
