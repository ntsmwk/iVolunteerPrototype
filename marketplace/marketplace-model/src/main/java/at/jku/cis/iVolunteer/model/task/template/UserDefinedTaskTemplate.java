package at.jku.cis.iVolunteer.model.task.template;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author alexander
 *
 */
@Document
public class UserDefinedTaskTemplate {

	@Id
	String id;
	String name;
	String description;
	
	public UserDefinedTaskTemplate() {}
	
	public UserDefinedTaskTemplate(String id) {
		this.id = id;
	}

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
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserDefinedTaskTemplate)) {
			return false;
		}
		return ((UserDefinedTaskTemplate) obj).id.equals(id);
	}

	@Override
	public String toString() {
		String s = "UserDefinedTaskTemplate [id=" + id + ", name=" + name + ", description=" + description + ", \n";
		if (this instanceof SingleUserDefinedTaskTemplate) {
			s = s + ((SingleUserDefinedTaskTemplate)this).properties;
		} else if (this instanceof MultiUserDefinedTaskTemplate) {
			s = s + ((MultiUserDefinedTaskTemplate)this).templates;
		}
		return s;
	}
	
	
	
	
	
	
}
