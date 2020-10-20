package at.jku.cis.iVolunteer.model.chart.xnet;

public class XDataPoint {
    private String name;
    private double value;

    public XDataPoint() {
    }

    public XDataPoint(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
