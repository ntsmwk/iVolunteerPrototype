package at.jku.cis.trustifier.model.participant.profile;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.cis.trustifier.hash.IHashObject;


public class CompetenceEntry implements IHashObject {

	private String id;

	private String competenceId;
	private String competenceName;

	private Date timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompetenceId() {
		return competenceId;
	}

	public void setCompetenceId(String competenceId) {
		this.competenceId = competenceId;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompetenceEntry)) {
			return false;
		}
		return ((CompetenceEntry) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("competenceId", competenceId);
		json.addProperty("competenceName", competenceName);
		json.addProperty("timestamp", timestamp.toString());
		return json.toString();
	}

}
