package at.jku.cis.iVolunteer.model.rule.entities;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class GeneralConditionDTO{
	
	private PropertyDefinition<Object> propertyDefinition;
	private ComparisonOperatorType comparisonOperatorType;
	private Object value;

	public GeneralConditionDTO() {
		
	}
	
	public GeneralConditionDTO(PropertyDefinition<Object> propertyDefinition, Object value, ComparisonOperatorType comparisonOperatorType) {
		this.propertyDefinition = propertyDefinition;
		this.comparisonOperatorType = comparisonOperatorType;
		this.value = value;
	}
    
    public PropertyDefinition<Object> getPropertyDefinition() {
    	return propertyDefinition;
    }
    
    public void setPropertyDefinition(PropertyDefinition<Object> propertyDefinition) {
    	this.propertyDefinition = propertyDefinition;
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