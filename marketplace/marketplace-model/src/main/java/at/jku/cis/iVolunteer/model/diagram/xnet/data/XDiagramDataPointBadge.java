package at.jku.cis.iVolunteer.model.diagram.xnet.data;

import java.util.List;

import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;

public class XDiagramDataPointBadge extends XDiagramData {
    private List<XBadgeCertificate> badges;

    public XDiagramDataPointBadge() {
    }

    public XDiagramDataPointBadge(String name, List<XBadgeCertificate> badges) {
        super(name);
        this.badges = badges;
    }

    public List<XBadgeCertificate> getBadges() {
        return this.badges;
    }

    public void setBadges(List<XBadgeCertificate> badges) {
        this.badges = badges;
    }

}
