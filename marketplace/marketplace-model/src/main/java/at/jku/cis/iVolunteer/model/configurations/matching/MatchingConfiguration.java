package at.jku.cis.iVolunteer.model.configurations.matching;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MatchingConfiguration {
	@Id private String id;
	private String name;
	private Date timestamp;

	private String leftClassConfigurationId;
	private String leftClassConfigurationName;

	private String rightClassConfigurationId;
	private String rightClassConfigurationName;

	List<MatchingOperatorRelationship> relationships;

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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<MatchingOperatorRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<MatchingOperatorRelationship> relationships) {
		this.relationships = relationships;
	}
	
	
	
	public String getLeftClassConfigurationId() {
		return leftClassConfigurationId;
	}

	public void setLeftClassConfigurationId(String leftClassConfigurationId) {
		this.leftClassConfigurationId = leftClassConfigurationId;
	}

	public String getLeftClassConfigurationName() {
		return leftClassConfigurationName;
	}

	public void setLeftClassConfigurationName(String leftClassConfigurationName) {
		this.leftClassConfigurationName = leftClassConfigurationName;
	}

	public String getRightClassConfigurationId() {
		return rightClassConfigurationId;
	}

	public void setRightClassConfigurationId(String rightClassConfigurationId) {
		this.rightClassConfigurationId = rightClassConfigurationId;
	}

	public String getRightClassConfigurationName() {
		return rightClassConfigurationName;
	}

	public void setRightClassConfigurationName(String rightClassConfigurationName) {
		this.rightClassConfigurationName = rightClassConfigurationName;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingConfiguration)) {
			return false;
		}
		return ((MatchingConfiguration) obj).id.equals(id);
	}

}
