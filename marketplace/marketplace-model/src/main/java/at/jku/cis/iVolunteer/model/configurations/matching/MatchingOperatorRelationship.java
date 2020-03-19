package at.jku.cis.iVolunteer.model.configurations.matching;

public class MatchingOperatorRelationship {

	String producerPath;
	MatchingProducerConsumerType producerType;
	
	String consumerPath;
	MatchingProducerConsumerType consumerType;
	
	MatchingOperatorType matchingOperatorType;
	
	float weighting;
	boolean necessary;
	float fuzzyness;
	
	int coordX;
	int coordY;
	
	public String getProducerPath() {
		return producerPath;
	}
	public void setProducerPath(String producerPath) {
		this.producerPath = producerPath;
	}
	public MatchingProducerConsumerType getProducerType() {
		return producerType;
	}
	public void setProducerType(MatchingProducerConsumerType producerType) {
		this.producerType = producerType;
	}
	public String getConsumerPath() {
		return consumerPath;
	}
	public void setConsumerPath(String consumerPath) {
		this.consumerPath = consumerPath;
	}
	public MatchingProducerConsumerType getConsumerType() {
		return consumerType;
	}
	public void setConsumerType(MatchingProducerConsumerType consumerType) {
		this.consumerType = consumerType;
	}
	public MatchingOperatorType getMatchingOperatorType() {
		return matchingOperatorType;
	}
	public void setMatchingOperatorType(MatchingOperatorType matchingOperatorType) {
		this.matchingOperatorType = matchingOperatorType;
	}
	public float getWeighting() {
		return weighting;
	}
	public void setWeighting(float weighting) {
		this.weighting = weighting;
	}
	public boolean isNecessary() {
		return necessary;
	}
	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}
	public float getFuzzyness() {
		return fuzzyness;
	}
	public void setFuzzyness(float fuzzyness) {
		this.fuzzyness = fuzzyness;
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
