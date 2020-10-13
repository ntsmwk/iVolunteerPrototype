package at.jku.cis.iVolunteer.model.rule.entities;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;

public class ClassConditionDTO {
	
	private ClassDefinition classDefinition;
	private AggregationOperatorType aggregationOperatorType;
	private Object value;
	private ClassProperty<Object> classProperty; // only for aggregation SUM (over property)
	private List<AttributeConditionDTO> attributeConditions;
	
	public ClassConditionDTO() {
		attributeConditions = new ArrayList<AttributeConditionDTO>();
	}
	
	public ClassConditionDTO(ClassDefinition classDefinition, Object value, AggregationOperatorType aggregationOperatorType) {
		this();
		this.classDefinition = classDefinition;
		this.aggregationOperatorType = aggregationOperatorType;
		this.value = value;
	}
	
	public ClassDefinition getClassDefinition() {
    	return classDefinition;
    }
    
    public void setClassDefinition(ClassDefinition classDefinition) {
    	this.classDefinition = classDefinition;
    }
	
    public AggregationOperatorType getAggregationOperatorType() {
    	return aggregationOperatorType;
    }
    
    public void setAggregationOperatorType(AggregationOperatorType aggregationOperatorType) {
    	this.aggregationOperatorType = aggregationOperatorType;
    }
    
    public Object getValue() {
    	return value;
    }
    
    public void setValue(Object value) {
    	this.value = value;
    }
    
    public void setClassProperty(ClassProperty<Object> classProperty) {
    	this.classProperty = classProperty;
    }
    
    public ClassProperty<Object> getClassProperty() {
    	return classProperty;
    }
    
    public List<AttributeConditionDTO> getAttributeConditions(){
    	return attributeConditions;
    }
    
    public void setAttributeConditions(List<AttributeConditionDTO> attributeConditions) {
    	this.attributeConditions = attributeConditions;
    }
	
}