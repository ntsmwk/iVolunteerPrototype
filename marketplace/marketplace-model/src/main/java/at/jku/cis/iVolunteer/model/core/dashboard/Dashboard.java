package at.jku.cis.iVolunteer.model.core.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Document
public class Dashboard {

	@Id
	private String id;
	@DBRef
	private CoreUser user;
	private Date creationDate;
	private Date modificationDate;
	private List<Dashlet> dashlets;

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

	public CoreUser getUser() {
		return user;
	}

	public void setUser(CoreUser user) {
		this.user = user;
	}

	public List<Dashlet> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<Dashlet> dashlets) {
		this.dashlets = dashlets;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Dashboard)) {
			return false;
		}
		return ((Dashboard) obj).id.equals(id);
	}
}