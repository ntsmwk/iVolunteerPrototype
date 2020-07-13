package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

public class MultipleConditions extends Condition {
	
	LogicalOperatorType logicalOperator;
    List<Condition> conditions;
    
    public MultipleConditions(LogicalOperatorType logicalOperator) {
    	this.logicalOperator = logicalOperator;
    	conditions = new ArrayList<Condition>();
    }
	
    public LogicalOperatorType getLogicalOperator() {
    	return logicalOperator;
    }
	
    public void setLogicalOperator(LogicalOperatorType logicalOperator) {
    	this.logicalOperator = logicalOperator;
    }
    
    public void addCondition(Condition condition) {
    	conditions.add(condition);
    }
    
    public List<Condition> getConditions(){
    	return conditions;
    }
    
    public void setConditions(List<Condition> conditions) {
    	this.conditions = conditions;
    }

}
