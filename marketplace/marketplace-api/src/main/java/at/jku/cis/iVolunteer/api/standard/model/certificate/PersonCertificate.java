package at.jku.cis.iVolunteer.api.standard.model.certificate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PersonCertificate {

	@Id
	private String id;
	private String certificateID;
	private String certificateString;
	private String certificateName;
	private String certificateDescription;
	private String certificateIssuedOn;
	private String certificateValidUntil;
	private String certificateIcon;
	private String iVolunteerUUID;
	private String iVolunteerSource;
	private String personID;

	public PersonCertificate() {
	}

	public String getCertificateID() {
		return certificateID;
	}

	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}

	public String getCertificateString() {
		return certificateString;
	}

	public void setCertificateString(String certificateString) {
		this.certificateString = certificateString;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateDescription() {
		return certificateDescription;
	}

	public void setCertificateDescription(String certificateDescription) {
		this.certificateDescription = certificateDescription;
	}

	public String getCertificateIssuedOn() {
		return certificateIssuedOn;
	}

	public void setCertificateIssuedOn(String certificateIssuedOn) {
		this.certificateIssuedOn = certificateIssuedOn;
	}

	public String getCertificateValidUntil() {
		return certificateValidUntil;
	}

	public void setCertificateValidUntil(String certificateValidUntil) {
		this.certificateValidUntil = certificateValidUntil;
	}

	public String getCertificateIcon() {
		return certificateIcon;
	}

	public void setCertificateIcon(String certificateIcon) {
		this.certificateIcon = certificateIcon;
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
