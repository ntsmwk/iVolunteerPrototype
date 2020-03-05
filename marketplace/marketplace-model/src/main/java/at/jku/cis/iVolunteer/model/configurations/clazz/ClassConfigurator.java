package at.jku.cis.iVolunteer.model.configurations.clazz;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClassConfigurator {

	@Id String id;

	private String name;
	private String description;

	private List<String> classDefinitionIds;
	private List<String> relationshipIds;

	private Date date;
	
	private String userId;

	private ConfiguratorArcheType configuratorArcheType;

	public ClassConfigurator() {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassConfigurator)) {
			return false;
		}
		return ((ClassConfigurator) obj).id.equals(id);
	}

	public ConfiguratorArcheType getConfiguratorArcheType() {
		return configuratorArcheType;
	}

	public void setConfiguratorArcheType(ConfiguratorArcheType configuratorArcheType) {
		this.configuratorArcheType = configuratorArcheType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
