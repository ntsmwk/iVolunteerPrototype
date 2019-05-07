package at.jku.cis.iVolunteer.model.task.template.dto;

public class UserDefinedTaskTemplateStubDTO {

	String id;
	String name;
	
	String description;
	
	String kind;


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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKind() {
		return kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserDefinedTaskTemplateStubDTO)) {
			return false;
		}
		return ((UserDefinedTaskTemplateStubDTO) obj).id.equals(id);
	}
	
	
	
	
}
