package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class SourceRuleEntryDTO {

	private ClassDefinition classDefinition;
	private ClassProperty<Object> classProperty;
	private MappingOperatorType mappingOperatorType;
	private Object value;

	public SourceRuleEntryDTO() {
	}

	public SourceRuleEntryDTO(ClassDefinition classDefinition, ClassProperty<Object> classProperty,
			MappingOperatorType mappingOperatorType, String value) {
		super();
		this.classDefinition = classDefinition;
		this.classProperty = classProperty;
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}


	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}

	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}

	public ClassProperty<Object> getClassProperty() {
		return classProperty;
	}

	public void setClassProperty(ClassProperty<Object> classProperty) {
		this.classProperty = classProperty;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
