package at.jku.cis.iVolunteer.model.rule;

public class AttributeSourceRuleEntry extends SourceRuleEntry{

	
	private AttributeAggregationOperatorType aggregationOperatorType;

	public AttributeSourceRuleEntry() {
	}
	
	public AttributeAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(AttributeAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

}
