package at.jku.cis.iVolunteer.model.matching;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MatchingOperatorRelationshipStorage {
	@Id String id;
	String name;
	
	String producerConfiguratorId;
	String consumerConfiguratorId;
	
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

	public String getProducerConfiguratorId() {
		return producerConfiguratorId;
	}

	public void setProducerConfiguratorId(String producerConfiguratorId) {
		this.producerConfiguratorId = producerConfiguratorId;
	}

	public String getConsumerConfiguratorId() {
		return consumerConfiguratorId;
	}

	public void setConsumerConfiguratorId(String consumerConfiguratorId) {
		this.consumerConfiguratorId = consumerConfiguratorId;
	}

	public List<MatchingOperatorRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<MatchingOperatorRelationship> relationships) {
		this.relationships = relationships;
	}
	
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingOperatorRelationshipStorage)) {
			return false;
		}
		return ((MatchingOperatorRelationshipStorage) obj).id.equals(id);
	}
	

}
