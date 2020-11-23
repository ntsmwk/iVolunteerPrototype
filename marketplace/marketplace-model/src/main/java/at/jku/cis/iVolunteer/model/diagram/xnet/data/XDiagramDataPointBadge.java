package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.List;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.BadgeCertificate;

public class XDiagramDataPointBadge extends XDiagramData {
    private List<BadgeCertificate> badges;

    public XDiagramDataPointBadge() {
    }

    public XDiagramDataPointBadge(String name, List<BadgeCertificate> badges) {
        super(name);
        this.badges = badges;
    }

    public List<BadgeCertificate> getBadges() {
        return this.badges;
    }

    public void setBadges(List<BadgeCertificate> badges) {
        this.badges = badges;
    }

}
