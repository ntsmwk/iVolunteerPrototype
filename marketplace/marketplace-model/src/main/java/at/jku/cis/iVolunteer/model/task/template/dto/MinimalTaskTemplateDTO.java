//TODO: Just for testing - DELETE


package at.jku.cis.iVolunteer.model.task.template.dto;


public class MinimalTaskTemplateDTO {
	
	private String id;
	private String name;
	private String description;
	
	//constructors for testing
	public MinimalTaskTemplateDTO() {
		this.id = "TEST";
		this.name = "TESTNAME";
		this.description = "Lorem ipsum dolor sit amet bla bla bla";
	}
	
	public MinimalTaskTemplateDTO(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	
	
	
	//Getters/Setters
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
	
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinimalTaskTemplateDTO)) {
			return false;
		}
		return ((MinimalTaskTemplateDTO) obj).id.equals(id);
	}
	
	
	
	
	

}
