package at.jku.cis.iVolunteer.model.task.template;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

/**
 * @author alexander
 *
 */
@Document
public class SingleUserDefinedTaskTemplate extends UserDefinedTaskTemplate {
	
	List<ClassProperty<Object>> templateProperties;
	
	public SingleUserDefinedTaskTemplate() {
	}
	
	public SingleUserDefinedTaskTemplate(UserDefinedTaskTemplate template) {
		super();
		this.id = template.getId();
		this.name = template.name;
		this.description = template.description;
	}
	
	public SingleUserDefinedTaskTemplate(String id) {
		super();
		this.id = id;
	}

	public List<ClassProperty<Object>> getTemplateProperties() {
		return templateProperties;
	}

	public void setTemplateProperties(List<ClassProperty<Object>> templateProperties) {
		this.templateProperties = templateProperties;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SingleUserDefinedTaskTemplate)) {
			return false;
		}
		return ((SingleUserDefinedTaskTemplate) obj).id.equals(id);
	}
	
	
	
	
}
