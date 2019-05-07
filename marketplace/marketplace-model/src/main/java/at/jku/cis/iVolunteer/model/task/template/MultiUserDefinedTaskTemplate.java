package at.jku.cis.iVolunteer.model.task.template;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;

/**
 * @author alexander
 *
 */
@Document
public class MultiUserDefinedTaskTemplate extends UserDefinedTaskTemplate {
	
	List<SingleUserDefinedTaskTemplate> templates;
	
	public MultiUserDefinedTaskTemplate() {}
	
	public MultiUserDefinedTaskTemplate(UserDefinedTaskTemplate template) {
		this.id = template.id;
		this.name = template.name;
		this.description = template.description;
	}
	
	public MultiUserDefinedTaskTemplate(String id) {
		this.id = id;
	}

	public List<SingleUserDefinedTaskTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<SingleUserDefinedTaskTemplate> templates) {
		this.templates = templates;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiUserDefinedTaskTemplate)) {
			return false;
		}
		return ((MultiUserDefinedTaskTemplate) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "MultiUserDefinedTaskTemplate [templates=" + templates + "]";
	}
	
	
	
	
	
	
}
