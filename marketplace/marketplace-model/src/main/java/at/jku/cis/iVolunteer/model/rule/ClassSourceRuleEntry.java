package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

public class ClassSourceRuleEntry extends SourceRuleEntry {

	private List<AttributeSourceRuleEntry> attributeSourceRules = new ArrayList<>();
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
	
	public List<AttributeSourceRuleEntry> getAttributeSourceRules() {
		return attributeSourceRules;
	}

	public void setAttributeSourceRules(List<AttributeSourceRuleEntry> attributeSourceRules) {
		this.attributeSourceRules = attributeSourceRules;
	}

}
