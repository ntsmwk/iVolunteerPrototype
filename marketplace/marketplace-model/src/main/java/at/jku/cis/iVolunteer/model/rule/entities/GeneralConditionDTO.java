package at.jku.cis.iVolunteer.model.rule.entities;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

public class GeneralConditionDTO{
	
	private FlatPropertyDefinition<Object> propertyDefinition;
	private ComparisonOperatorType comparisonOperatorType;
	private Object value;

	public GeneralConditionDTO() {
		
	}
	
	public GeneralConditionDTO(FlatPropertyDefinition<Object> propertyDefinition, Object value, ComparisonOperatorType comparisonOperatorType) {
		this.propertyDefinition = propertyDefinition;
		this.comparisonOperatorType = comparisonOperatorType;
		this.value = value;
	}
    
    public FlatPropertyDefinition<Object> getPropertyDefinition() {
    	return propertyDefinition;
    }
    
    public void setPropertyDefinition(FlatPropertyDefinition<Object> propertyDefinition) {
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