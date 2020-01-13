package at.jku.cis.iVolunteer.model.rule;

public class ClassSourceRuleEntry extends SourceRuleEntry {

	private MappingOperatorType mappingOperatorType;
	private ClassAggregationOperatorType aggregationOperatorType;

	public ClassSourceRuleEntry() {
	}

	public ClassSourceRuleEntry(String classDefinitionId, Object value,
			MappingOperatorType mappingOperatorType, ClassAggregationOperatorType aggregationOperatorType) {
		super(classDefinitionId, value);
		this.aggregationOperatorType = aggregationOperatorType;
		this.mappingOperatorType = mappingOperatorType;
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
