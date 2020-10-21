package at.jku.cis.iVolunteer.model.chart.xnet;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class XChartDataSet {
    @Id
    private String id;
    private String name;
    private String userId;
    private String tenantId;
    private Date timestamp;
    private List<XDataPoint> datapoints;

    public XChartDataSet() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public XChartDataSet(List<XDataPoint> datapoints) {
        this.datapoints = datapoints;
    }

    public List<XDataPoint> getDatapoints() {
        return this.datapoints;
    }

    public void setDatapoints(List<XDataPoint> datapoints) {
        this.datapoints = datapoints;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof XChartDataSet)) {
            return false;
        }
        XChartDataSet xChartData = (XChartDataSet) o;
        return Objects.equals(name, xChartData.name) && Objects.equals(tenantId, xChartData.tenantId)
                && Objects.equals(userId, xChartData.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId, tenantId);
    }

}
