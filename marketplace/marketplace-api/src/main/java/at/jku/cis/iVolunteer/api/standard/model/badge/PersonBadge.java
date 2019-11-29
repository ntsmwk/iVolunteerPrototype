package at.jku.cis.iVolunteer.api.standard.model.badge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PersonBadge {

	@Id
	private String id;
	private String badgeID;
	private String badgeName;
	private String badgeDescription;
	private String badgeIssuedOn;
	private String badgeIcon;
	private String iVolunteerUUID;
	private String iVolunteerSource;

	private String personID;

	public PersonBadge() {
	}

	public String getBadgeID() {
		return badgeID;
	}

	public void setBadgeID(String badgeID) {
		this.badgeID = badgeID;
	}

	public String getBadgeName() {
		return badgeName;
	}

	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public String getBadgeDescription() {
		return badgeDescription;
	}

	public void setBadgeDescription(String badgeDescription) {
		this.badgeDescription = badgeDescription;
	}

	public String getBadgeIssuedOn() {
		return badgeIssuedOn;
	}

	public void setBadgeIssuedOn(String badgeIssuedOn) {
		this.badgeIssuedOn = badgeIssuedOn;
	}

	public String getBadgeIcon() {
		return badgeIcon;
	}

	public void setBadgeIcon(String badgeIcon) {
		this.badgeIcon = badgeIcon;
	}

	public String getiVolunteerUUID() {
		return iVolunteerUUID;
	}

	public void setiVolunteerUUID(String iVolunteerUUID) {
		this.iVolunteerUUID = iVolunteerUUID;
	}

	public String getiVolunteerSource() {
		return iVolunteerSource;
	}

	public void setiVolunteerSource(String iVolunteerSource) {
		this.iVolunteerSource = iVolunteerSource;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

}
