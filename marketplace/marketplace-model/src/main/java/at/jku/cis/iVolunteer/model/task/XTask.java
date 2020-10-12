package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;
import at.jku.cis.iVolunteer.model.user.XUser;

public class XTask {
	String id;
	String title;
	String tenant;
	String descripiton;
	Date startDate;
	Date endDate;
	String imagePath;
	boolean closed;
	XGeoInfo geoInfo;
	
	List<XDynamicFieldBlock> dynamicFields = new ArrayList<>();
	List<XUser> subscribedUsers;
	List<XBadgeTemplate> badges = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public String getDescripiton() {
		return descripiton;
	}
	public void setDescripiton(String descripiton) {
		this.descripiton = descripiton;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public XGeoInfo getGeoInfo() {
		return geoInfo;
	}
	public void setGeoInfo(XGeoInfo geoInfo) {
		this.geoInfo = geoInfo;
	}
	public List<XDynamicFieldBlock> getDynamicFields() {
		return dynamicFields;
	}
	public void setDynamicFields(List<XDynamicFieldBlock> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}
	public List<XUser> getSubscribedUsers() {
		return subscribedUsers;
	}
	public void setSubscribedUsers(List<XUser> subscribedUsers) {
		this.subscribedUsers = subscribedUsers;
	}
	public List<XBadgeTemplate> getBadges() {
		return badges;
	}
	public void setBadges(List<XBadgeTemplate> badges) {
		this.badges = badges;
	}

}
