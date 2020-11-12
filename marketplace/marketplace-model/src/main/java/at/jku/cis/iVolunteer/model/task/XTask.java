package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;
import at.jku.cis.iVolunteer.model.user.XUser;

public class XTask {
	String id;
	String title;
	private String tenantId;
	String description;
	Date startDate;
	Date endDate;
	String imagePath;
	private TaskInstanceStatus status;
	XGeoInfo geoInfo;

	private List<XDynamicFieldBlock> dynamicBlocks = new ArrayList<>();
	List<XUser> subscribedUsers = new ArrayList<>();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public XGeoInfo getGeoInfo() {
		return geoInfo;
	}

	public void setGeoInfo(XGeoInfo geoInfo) {
		this.geoInfo = geoInfo;
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

	public List<XDynamicFieldBlock> getDynamicBlocks() {
		return dynamicBlocks;
	}

	public void setDynamicBlocks(List<XDynamicFieldBlock> dynamicBlocks) {
		this.dynamicBlocks = dynamicBlocks;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TaskInstanceStatus getStatus() {
		return status;
	}

	public void setStatus(TaskInstanceStatus status) {
		this.status = status;
	}

}
