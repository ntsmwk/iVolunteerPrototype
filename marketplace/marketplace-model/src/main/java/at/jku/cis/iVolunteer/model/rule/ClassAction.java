package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

public class ClassAction extends Action {

	private String classDefinitionId;
	private List<AttributeCondition> attributeConditions;
	
	public ClassAction(ActionType type) {
		super(type);
		attributeConditions = new ArrayList<AttributeCondition>();
	}
	
	public ClassAction(String classDefinitionId, ActionType type) {
		this(type);
		this.classDefinitionId = classDefinitionId;
	}

	public ClassAction() {
		attributeConditions = new ArrayList<AttributeCondition>();
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}
	
	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}
	
	public void addAttributeCondition(AttributeCondition attrCondition) {
		attributeConditions.add(attrCondition);
	}
	
	public void setAttributeConditions(List<AttributeCondition> attributeConditions) {
		this.attributeConditions = attributeConditions;
	}
	
	public List<AttributeCondition> getAttributeConditions(){
		return attributeConditions;
	}

}
