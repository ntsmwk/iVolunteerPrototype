package at.jku.cis.iVolunteer.model.user;

public class XUserRole {
    private String name;
    private String nameShort;

    public XUserRole(String name, String nameShort) {
        this.name = name;
        this.nameShort = nameShort;
    }

    public XUserRole() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShort() {
        return this.nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

}
