package at.jku.cis.iVolunteer.model.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.group.interaction.GroupInteraction;
import at.jku.cis.iVolunteer.model.user.User;

@Document
public class Group {
	
	@Id
	private String id;
	
	private String name;
	private String description;
	private boolean autoJoin;
	
	@DBRef
	private List<GroupInteraction> user = new ArrayList<>();
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<GroupInteraction> getUser() {
		return user;
	}
	public void setUser(List<GroupInteraction> user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getAutoJoin() {
		return autoJoin;
	}
	public void setAutoJoin(boolean autoJoin) {
		this.autoJoin = autoJoin;
	}
	
	
	
}
