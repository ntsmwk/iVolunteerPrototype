package at.jku.cis.iVolunteer.model.core.dashboard.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.core.user.dto.CoreUserDTO;

public class DashboardDTO {

	private String id;
	private String name;
	private CoreUserDTO user;
	private List<DashletDTO> dashlets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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