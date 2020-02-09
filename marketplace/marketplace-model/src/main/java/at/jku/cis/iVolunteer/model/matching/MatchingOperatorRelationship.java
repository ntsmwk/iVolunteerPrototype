package at.jku.cis.iVolunteer.model.matching;

public class MatchingOperatorRelationship {

	String producerId;
	String consumerId;
	String matchingOperatorType;
	int coordX;
	int coordY;
	
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
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	
	
	
}
