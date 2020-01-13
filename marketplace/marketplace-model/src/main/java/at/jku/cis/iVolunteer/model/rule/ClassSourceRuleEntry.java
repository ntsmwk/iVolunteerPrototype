package at.jku.cis.iVolunteer.model.rule;

public class ClassSourceRuleEntry extends SourceRuleEntry {

	private ClassAggregationOperatorType aggregationOperatorType;
	private MappingOperatorType mappingOperatorType;

	public ClassSourceRuleEntry() {
	}

	public ClassAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(ClassAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}

	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}

}
