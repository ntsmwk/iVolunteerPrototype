package at.jku.cis.iVolunteer.model.configurations.matching;

import org.springframework.data.annotation.Id;

public class MatchingOperatorRelationship {

	@Id private String id;

	private String leftMatchingEntityPath;
	private MatchingEntityType leftMatchingEntityType;

	private String rightMatchingEntityPath;
	private MatchingEntityType rightMatchingEntityType;

	private MatchingOperatorType matchingOperatorType;

	private float weighting;
	private boolean necessary;
	private float fuzzyness;

	private int coordX;
	private int coordY;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLeftMatchingEntityPath() {
		return leftMatchingEntityPath;
	}

	public void setLeftMatchingEntityPath(String leftMatchingEntityPath) {
		this.leftMatchingEntityPath = leftMatchingEntityPath;
	}

	public MatchingEntityType getLeftMatchingEntityType() {
		return leftMatchingEntityType;
	}

	public void setLeftMatchingEntityType(MatchingEntityType leftMatchingEntityType) {
		this.leftMatchingEntityType = leftMatchingEntityType;
	}

	public String getRightMatchingEntityPath() {
		return rightMatchingEntityPath;
	}

	public void setRightMatchingEntityPath(String rightMatchingEntityPath) {
		this.rightMatchingEntityPath = rightMatchingEntityPath;
	}

	public MatchingEntityType getRightMatchingEntityType() {
		return rightMatchingEntityType;
	}

	public void setRightMatchingEntityType(MatchingEntityType rightMatchingEntityType) {
		this.rightMatchingEntityType = rightMatchingEntityType;
	}

}
