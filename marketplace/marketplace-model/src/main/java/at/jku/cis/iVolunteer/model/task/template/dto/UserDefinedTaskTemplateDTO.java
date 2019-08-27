package at.jku.cis.iVolunteer.model.task.template.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyDTO;

public class UserDefinedTaskTemplateDTO {

	String id;
	String name;
	String description;
	
	String kind;
	
	List<PropertyDTO<Object>> properties;
	List<SingleUserDefinedTaskTemplateDTO> templates;

	int order;
	
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
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return kind;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<PropertyDTO<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDTO<Object>> properties) {
		this.properties = properties;
	}
	
	public List<SingleUserDefinedTaskTemplateDTO> getTemplates() {
		return templates;
	}
	
	public void setTemplates(List<SingleUserDefinedTaskTemplateDTO> templates) {
		this.templates = templates;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserDefinedTaskTemplateDTO)) {
			return false;
		}
		return ((UserDefinedTaskTemplateDTO) obj).id.equals(id);
	}
	
	
	
	
}
