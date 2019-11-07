package at.jku.cis.iVolunteer.model.task.template.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyInstanceDTO;

/**
 * @author alexander
 *
 */
@Document
public class SingleUserDefinedTaskTemplateDTO {
	
	String id;
	String name;
	String description;
	
	String kind;
	
	List<ClassPropertyDTO<Object>> templateProperties;
	
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return kind;
	}


	public List<ClassPropertyDTO<Object>> getTemplateProperties() {
		return templateProperties;
	}

	public void setTemplateProperties(List<ClassPropertyDTO<Object>> templateProperties) {
		this.templateProperties = templateProperties;
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
		if (!(obj instanceof SingleUserDefinedTaskTemplateDTO)) {
			return false;
		}
		return ((SingleUserDefinedTaskTemplateDTO) obj).id.equals(id);
	}
	
	
	
	
}
