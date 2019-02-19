package at.jku.cis.iVolunteer.model.group.interaction;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.group.Group;
import at.jku.cis.iVolunteer.model.user.User;

@Document
public class GroupInteraction {

	@Id
	private String id;
	
	@DBRef
	private User user;
	private GroupStatus status;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public GroupStatus getStatus() {
		return status;
	}
	
	public void setStatus(GroupStatus status) {
		this.status = status;
	}
	
	
}
