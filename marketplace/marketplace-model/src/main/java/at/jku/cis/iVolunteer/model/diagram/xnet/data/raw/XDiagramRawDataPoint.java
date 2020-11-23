package at.jku.cis.iVolunteer.model.diagram.xnet.data.raw;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyEntry;

@Component
public class XDiagramRawDataPoint {

    @Autowired
    ObjectMapper objectMapper;

    private String tenantId;
    private Date startDate;
    private Date endDate;
    private float duration;
    private String bereich;
    private TreePropertyEntry treeProperty;

    public XDiagramRawDataPoint() {
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float Duration) {
        this.duration = Duration;
    }

    public String getBereich() {
        return this.bereich;
    }

    public void setBereich(String Bereich) {
        this.bereich = Bereich;
    }

    public TreePropertyEntry getTreeProperty() {
        return this.treeProperty;
    }

    public void setTreeProperty(TreePropertyEntry treeProperty) {
        this.treeProperty = treeProperty;
    }

}
