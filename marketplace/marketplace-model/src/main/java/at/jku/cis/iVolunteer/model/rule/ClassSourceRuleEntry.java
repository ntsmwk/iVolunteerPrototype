package at.jku.cis.iVolunteer.model.rule;

public class ClassSourceRuleEntry extends SourceRuleEntry {

	private ClassAggregationOperatorType aggregationOperatorType;

	public ClassSourceRuleEntry() {
	}

	public ClassAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(ClassAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

}
