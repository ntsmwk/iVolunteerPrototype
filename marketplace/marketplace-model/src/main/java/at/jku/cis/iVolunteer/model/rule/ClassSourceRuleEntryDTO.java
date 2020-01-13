package at.jku.cis.iVolunteer.model.rule;

public class ClassSourceRuleEntryDTO extends SourceRuleEntryDTO {

	private MappingOperatorType mappingOperatorType;
	private Object value;
	private ClassAggregationOperatorType aggregationOperatorType;

	public ClassSourceRuleEntryDTO() {
	}
	
	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}

	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ClassAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(ClassAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}
}