package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

public class SourceRuleEntryDTO {

	private ClassDefinition classDefinition;
	private PropertyDefinition<Object> propertyDefinition;
	private MappingOperatorType mappingOperatorType;
	private String value;

	public SourceRuleEntryDTO() {
	}

	public SourceRuleEntryDTO(ClassDefinition classDefinition, PropertyDefinition<Object> propertyDefinition,
			MappingOperatorType mappingOperatorType, String value) {
		super();
		this.classDefinition = classDefinition;
		this.propertyDefinition = propertyDefinition;
		this.mappingOperatorType = mappingOperatorType;
		this.value = value;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public PropertyDefinition<Object> getPropertyDefinition() {
		return propertyDefinition;
	}

	public void setPropertyDefinition(PropertyDefinition<Object> propertyDefinition) {
		this.propertyDefinition = propertyDefinition;
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

}
