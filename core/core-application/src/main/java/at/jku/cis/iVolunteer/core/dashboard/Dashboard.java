package at.jku.cis.iVolunteer.core.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Dashboard {

	@Id
	private String id;
	private String name;
	private List<Dashlet> dashlets;
	private Date creationDate;
	private Date modificationDate;

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

	public List<Dashlet> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<Dashlet> dashlets) {
		this.dashlets = dashlets;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Dashboard)) {
			return false;
		}
		return ((Dashboard) obj).id.equals(id);
	}
}