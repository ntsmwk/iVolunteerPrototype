package at.jku.cis.iVolunteer.model.matching;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MatchingConfigurator {
	@Id String id;
	String name;
	
	String producerClassConfiguratorId;
	String producerClassConfiguratorName;
	
	String consumerClassConfiguratorId;
	String consumerClassConfiguratorName;
	
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

	public List<MatchingOperatorRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<MatchingOperatorRelationship> relationships) {
		this.relationships = relationships;
	}
	
	public String getProducerClassConfiguratorId() {
		return producerClassConfiguratorId;
	}

	public void setProducerClassConfiguratorId(String producerClassConfiguratorId) {
		this.producerClassConfiguratorId = producerClassConfiguratorId;
	}

	public String getProducerClassConfiguratorName() {
		return producerClassConfiguratorName;
	}

	public void setProducerClassConfiguratorName(String producerClassConfiguratorName) {
		this.producerClassConfiguratorName = producerClassConfiguratorName;
	}

	public String getConsumerClassConfiguratorId() {
		return consumerClassConfiguratorId;
	}

	public void setConsumerClassConfiguratorId(String consumerClassConfiguratorId) {
		this.consumerClassConfiguratorId = consumerClassConfiguratorId;
	}

	public String getConsumerClassConfiguratorName() {
		return consumerClassConfiguratorName;
	}

	public void setConsumerClassConfiguratorName(String consumerClassConfiguratorName) {
		this.consumerClassConfiguratorName = consumerClassConfiguratorName;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingConfigurator)) {
			return false;
		}
		return ((MatchingConfigurator) obj).id.equals(id);
	}
	

}
