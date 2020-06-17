package at.jku.cis.iVolunteer.model.configurations.clazz;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class ClassConfiguration extends IVolunteerObject {

	private String name;

	private List<String> classDefinitionIds;
	private List<String> relationshipIds;

	private String userId;

	private ClassConfigurationArcheType configuratorArcheType;

	public ClassConfiguration() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getClassDefinitionIds() {
		return classDefinitionIds;
	}

	public void setClassDefinitionIds(List<String> classDefinitionIds) {
		this.classDefinitionIds = classDefinitionIds;
	}

	public List<String> getRelationshipIds() {
		return relationshipIds;
	}

	public void setRelationshipIds(List<String> relationshipIds) {
		this.relationshipIds = relationshipIds;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassConfiguration)) {
			return false;
		}
		return ((ClassConfiguration) obj).id.equals(id);
	}

	public ClassConfigurationArcheType getConfiguratorArcheType() {
		return configuratorArcheType;
	}

	public void setConfiguratorArcheType(ClassConfigurationArcheType configuratorArcheType) {
		this.configuratorArcheType = configuratorArcheType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
