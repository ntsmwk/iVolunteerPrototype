package at.jku.cis.iVolunteer.model.rule;

public class AttributeSourceRuleEntry extends SourceRuleEntry {

	private AttributeAggregationOperatorType aggregationOperatorType;

	public AttributeSourceRuleEntry() {
	}

	public AttributeSourceRuleEntry(String classDefinitionId, String classPropertyId, MappingOperatorType mappingOperatorType,
			String value, AttributeAggregationOperatorType aggregationOperatorType) {
		super(classDefinitionId, classPropertyId, mappingOperatorType, value);
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public AttributeAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(AttributeAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

}
