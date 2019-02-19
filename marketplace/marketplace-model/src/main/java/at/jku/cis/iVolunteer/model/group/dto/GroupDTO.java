package at.jku.cis.iVolunteer.model.group.dto;

public class GroupDTO{
	
	private String id;
	private String name;
	private String description;
	private boolean autoJoin;
	
	
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
