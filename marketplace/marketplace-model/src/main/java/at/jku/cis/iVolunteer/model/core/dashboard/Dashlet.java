package at.jku.cis.iVolunteer.model.core.dashboard;

import java.util.HashMap;
import java.util.Map;

public class Dashlet extends DashletGridPosition {
	private String id;
	private String dashletId;
	private String name;
	private String type;
	private Map<String, Object> settings = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDashletId() {
		return dashletId;
	}

	public void setDashletId(String dashletId) {
		this.dashletId = dashletId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}
}
