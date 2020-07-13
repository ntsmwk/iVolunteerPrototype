package at.jku.cis.iVolunteer.model.configurations.matching;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MatchingConfiguration {
	@Id String id;
	String name;
	Date timestamp;

	String leftClassConfigurationId;
	String leftClassConfigurationName;

	String rightClassConfigurationId;
	String rightClassConfigurationName;

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

	public String getProducerClassConfigurationId() {
		return producerClassConfigurationId;
	}

	public void setProducerClassConfigurationId(String producerClassConfigurationId) {
		this.producerClassConfigurationId = producerClassConfigurationId;
	}

	public String getProducerClassConfigurationName() {
		return producerClassConfigurationName;
	}

	public void setProducerClassConfigurationName(String producerClassConfigurationName) {
		this.producerClassConfigurationName = producerClassConfigurationName;
	}

	public String getConsumerClassConfigurationId() {
		return consumerClassConfigurationId;
	}

	public void setConsumerClassConfigurationId(String consumerClassConfigurationId) {
		this.consumerClassConfigurationId = consumerClassConfigurationId;
	}

	public String getConsumerClassConfigurationName() {
		return consumerClassConfigurationName;
	}

	public void setConsumerClassConfigurationName(String consumerClassConfigurationName) {
		this.consumerClassConfigurationName = consumerClassConfigurationName;
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
