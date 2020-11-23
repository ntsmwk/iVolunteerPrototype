package at.jku.cis.iVolunteer.model.diagram.xnet.data.task;

public class XDiagramOrder {
    private String fieldname;
    private boolean asc = true; // default true

    public XDiagramOrder() {

    }

    public String getFieldname() {
        return this.fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public boolean isAsc() {
        return this.asc;
    }

    public boolean getAsc() {
        return this.asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
