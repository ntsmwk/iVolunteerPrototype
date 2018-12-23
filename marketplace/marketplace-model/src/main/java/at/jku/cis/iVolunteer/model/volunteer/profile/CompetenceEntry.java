package at.jku.cis.iVolunteer.model.volunteer.profile;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CompetenceEntry {

	private String id;
	private String competenceId;
	private String competenceName;
	private String marketplaceId;
	private Date timeStamp;
	
	// added
	private String volunteerId;
	
	public CompetenceEntry() {}
	
	public CompetenceEntry(String id, String competenceId, String marketplaceId, String volunteerId, Date timestamp) {
		setId(id);
		setCompetenceId(competenceId);
		setCompetenceName(competenceName);
		setMarketplaceId(marketplaceId);
		setVolunteerId(volunteerId);
		setTimestamp(timestamp);
	}

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

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public Date getTimestamp() {
		return timeStamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timeStamp = timestamp;
	}
	
	public String getVolunteerId() {
		return volunteerId;
	}
	
	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompetenceEntry)) {
			return false;
		}
		return ((CompetenceEntry) obj).id.equals(id);
	}
	
	public MultiValueMap<String, String> getProperties() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("timeStamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(getTimestamp()));
		map.add("competenceId", getCompetenceId());
		map.add("marketplaceId", getMarketplaceId());
		map.add("volunteerId", getVolunteerId());
		return map;
	}

	@Override
	public String toString() {
		return "CompetenceEntry [id=" + id + ", competenceId=" + competenceId + ", competenceName=" + competenceName
				+ ", marketplaceId=" + marketplaceId + ", volunteerId=" + volunteerId + ", timeStamp=" + timeStamp + "]";
	}
}
