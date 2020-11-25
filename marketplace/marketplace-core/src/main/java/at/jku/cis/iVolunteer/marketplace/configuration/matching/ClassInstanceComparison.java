package at.jku.cis.iVolunteer.marketplace.configuration.matching;

public class ClassInstanceComparison {

	private String leftClassInstanceId;
	private String rightClassInstanceId;

	public ClassInstanceComparison() {
	}

	public ClassInstanceComparison(String leftClassInstanceId, String rightClassInstanceId) {
		this.leftClassInstanceId = leftClassInstanceId;
		this.rightClassInstanceId = rightClassInstanceId;
	}

	public String getRightClassInstanceId() {
		return rightClassInstanceId;
	}

	public void setRightClassInstanceId(String rightClassInstanceId) {
		this.rightClassInstanceId = rightClassInstanceId;
	}

	public String getLeftClassInstanceId() {
		return leftClassInstanceId;
	}

	public void setLeftClassInstanceId(String leftClassInstanceId) {
		this.leftClassInstanceId = leftClassInstanceId;
	}

}
