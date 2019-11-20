package at.jku.cis.iVolunteer.model.task.template;


import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;


/**
 * @author alexander
 *
 */
@Document
public class UserDefinedTaskTemplate extends IVolunteerObject {

	String name;
	String description;
	private List<PropertyInstance<Object>> propertyInstances;
	
	int order;
	
	public UserDefinedTaskTemplate() {
	}
	
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
		if (!(obj instanceof UserDefinedTaskTemplate)) {
			return false;
		}
		return ((UserDefinedTaskTemplate) obj).id.equals(id);
	}

	@Override
	public String toString() {
		String s = "UserDefinedTaskTemplate [id=" + id + ", name=" + name + ", description=" + description + ", \n";
		if (this instanceof SingleUserDefinedTaskTemplate) {
			s = s + ((SingleUserDefinedTaskTemplate)this).templateProperties;
		} else if (this instanceof MultiUserDefinedTaskTemplate) {
			s = s + ((MultiUserDefinedTaskTemplate)this).templates;
		}
		return s;
	}

	public List<PropertyInstance<Object>> getPropertyInstances() {
		return propertyInstances;
	}

	public void setPropertyInstances(List<PropertyInstance<Object>> propertyInstances) {
		this.propertyInstances = propertyInstances;
	}
	
	
	
	
	
	
}
