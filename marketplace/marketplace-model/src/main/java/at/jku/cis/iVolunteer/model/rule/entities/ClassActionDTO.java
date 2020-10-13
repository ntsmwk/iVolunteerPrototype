package at.jku.cis.iVolunteer.model.rule.entities;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.rule.Action.ActionType;

public class ClassActionDTO {

	private ClassDefinition classDefinition;
	private List<AttributeConditionDTO> attributes;
	private ActionType actionType;

	public ClassActionDTO() {
		attributes = new ArrayList<AttributeConditionDTO>();
	}

	public ClassActionDTO(ActionType actionType, ClassDefinition classDefinition) {
		this();
		this.actionType = actionType;
		this.classDefinition = classDefinition;
	}

	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setAggregationOperatorType(ActionType actionType) {
		this.actionType = actionType;
	}

	public List<AttributeConditionDTO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeConditionDTO> list) {
		this.attributes = list;
	}

	/*
	 * public static class AttributeTarget{
	 * 
	 * private PropertyDefinition<Object> propertyDefinition; private Object value;
	 * 
	 * public AttributeTarget(PropertyDefinition<Object> propertyDefinition, Object
	 * value) { this.propertyDefinition = propertyDefinition; this.value = value; }
	 * 
	 * public PropertyDefinition<Object> getPropertyDefinition() { return
	 * propertyDefinition; }
	 * 
	 * public void setPropertyDefinition(PropertyDefinition<Object>
	 * propertyDefinition) { this.propertyDefinition = propertyDefinition; }
	 * 
	 * public Object getValue() { return value; }
	 * 
	 * public void setValue(Object value) { this.value = value; }
	 * 
	 * }
	 */

}