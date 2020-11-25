package at.jku.cis.iVolunteer.model.diagram.xnet.data.raw;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;

@Document
public class XDiagramRawDataSet {
   @Id
   private String id;
   private String userId;
   private Date refreshTimestamp;
   private List<XDiagramRawDataPoint> datapoints;
   private List<XBadgeCertificate> badges;

   public XDiagramRawDataSet() {
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public Date getRefreshTimestamp() {
      return this.refreshTimestamp;
   }

   public void setRefreshTimestamp(Date refreshTimestamp) {
      this.refreshTimestamp = refreshTimestamp;
   }

   public List<XDiagramRawDataPoint> getDatapoints() {
      return this.datapoints;
   }

   public void setDatapoints(List<XDiagramRawDataPoint> datapoints) {
      this.datapoints = datapoints;
   }

   public List<XBadgeCertificate> getBadges() {
      return this.badges;
   }

   public void setBadges(List<XBadgeCertificate> badges) {
      this.badges = badges;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this)
         return true;
      if (!(o instanceof XDiagramRawDataSet)) {
         return false;
      }
      XDiagramRawDataSet xDiagramRawDataSet = (XDiagramRawDataSet) o;
      return Objects.equals(userId, xDiagramRawDataSet.userId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userId);
   }

}
