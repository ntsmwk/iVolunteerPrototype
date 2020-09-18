package at.jku.cis.iVolunteer.model.rule.entities;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class AttributeConditionDTO {

	private ClassDefinition classDefinition;
	private ClassProperty<Object> classProperty;
	private ComparisonOperatorType comparisonOperatorType;
	private Object value;

	public AttributeConditionDTO() {

	}

	public AttributeConditionDTO(ClassDefinition classDefinition, ClassProperty<Object> classProperty, Object value,
			ComparisonOperatorType comparisonOperatorType) {
		this.classDefinition = classDefinition;
		this.classProperty = classProperty;
		this.comparisonOperatorType = comparisonOperatorType;
		this.value = value;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public ClassProperty<Object> getClassProperty() {
		return this.classProperty;
	}

	public void setClassProperty(ClassProperty<Object> classProperty) {
		this.classProperty = classProperty;
	}

	public ComparisonOperatorType getComparisonOperatorType() {
		return comparisonOperatorType;
	}

	public void setComparisonOperatorType(ComparisonOperatorType comparisonOperatorType) {
		this.comparisonOperatorType = comparisonOperatorType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
