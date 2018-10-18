package at.jku.cis.iVolunteer.model.core.dashboard.dto;

import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.core.user.dto.CoreUserDTO;

public class DashboardDTO {

	private String id;
	private CoreUserDTO user;
	private Date creationDate;
	private Date modificationDate;
	private List<DashletDTO> dashlets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public CoreUserDTO getUser() {
		return user;
	}

	public void setUser(CoreUserDTO user) {
		this.user = user;
	}

	public List<DashletDTO> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<DashletDTO> dashlets) {
		this.dashlets = dashlets;
	}
}