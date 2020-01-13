package at.jku.cis.iVolunteer.model.rule;

public class AttributeSourceRuleEntry extends SourceRuleEntry {

	private String classPropertyId;
	private MappingOperatorType mappingOperatorType;
	private AttributeAggregationOperatorType aggregationOperatorType;

	public AttributeSourceRuleEntry() {
	}

	public AttributeSourceRuleEntry(String classDefinitionId, Object value,
			String classPropertyId, MappingOperatorType mappingOperatorType,
			AttributeAggregationOperatorType aggregationOperatorType) {
		super(classDefinitionId, value);
		this.classPropertyId = classPropertyId;
		this.mappingOperatorType = mappingOperatorType;
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public AttributeSourceRuleEntry(String classDefinitionId, String classPropertyId,
			MappingOperatorType mappingOperatorType, String value,
			AttributeAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public AttributeAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(AttributeAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public String getClassPropertyId() {
		return classPropertyId;
	}

	public void setClassPropertyId(String classPropertyId) {
		this.classPropertyId = classPropertyId;
	}

	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}

	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}

}
