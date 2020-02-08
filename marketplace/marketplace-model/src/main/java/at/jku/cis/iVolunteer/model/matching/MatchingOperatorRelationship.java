package at.jku.cis.iVolunteer.model.matching;

public class MatchingOperatorRelationship {

	String producerId;
	String consumerId;
	String matchingOperatorType;
	
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getMatchingOperatorType() {
		return matchingOperatorType;
	}
	public void setMatchingOperatorType(String matchingOperatorType) {
		this.matchingOperatorType = matchingOperatorType;
	}
	
	
}
