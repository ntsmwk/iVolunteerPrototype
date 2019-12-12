package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

public class SourceRuleEntryDTO {

	private ClassDefinition classDefinition;
	private ClassProperty<Object> classProperty;
	private MappingOperatorType mappingOperatorType;
	private String value;

	public SourceRuleEntryDTO() {
	}

	public SourceRuleEntryDTO(ClassDefinition classDefinition, ClassProperty<Object> classProperty,
			MappingOperatorType mappingOperatorType, String value) {
		super();
		this.classDefinition = classDefinition;
		this.setClassProperty(classProperty);
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}