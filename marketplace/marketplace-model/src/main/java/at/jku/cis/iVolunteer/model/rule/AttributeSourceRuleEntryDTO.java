package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class AttributeSourceRuleEntryDTO extends SourceRuleEntryDTO {

	private ClassProperty<Object> classProperty;
	private MappingOperatorType mappingOperatorType;
	private Object value;
	private AttributeAggregationOperatorType aggregationOperatorType;

	public AttributeSourceRuleEntryDTO() {
	}

	public AttributeSourceRuleEntryDTO(ClassDefinition classDefinition, ClassProperty<Object> classProperty,
			MappingOperatorType mappingOperatorType, Object value,
			AttributeAggregationOperatorType aggregationOperatorType) {
		super(classDefinition);
		this.classProperty = classProperty;
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
		this.aggregationOperatorType = aggregationOperatorType;
	}

	public ClassProperty<Object> getClassProperty() {
		return classProperty;
	}

	public void setClassProperty(ClassProperty<Object> classProperty) {
		this.classProperty = classProperty;
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

	public AttributeAggregationOperatorType getAggregationOperatorType() {
		return aggregationOperatorType;
	}

	public void setAggregationOperatorType(AttributeAggregationOperatorType aggregationOperatorType) {
		this.aggregationOperatorType = aggregationOperatorType;
	}

}