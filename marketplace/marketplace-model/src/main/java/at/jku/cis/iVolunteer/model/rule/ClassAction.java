package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

public class ClassAction extends Action {

	private String classDefinitionId;
	private List<AttributeCondition> attributes = new ArrayList<AttributeCondition>();
	
	public ClassAction(ActionType type) {
		super(type);
	}
	
	public ClassAction(String classDefinitionId, ActionType type) {
		this(type);
		this.classDefinitionId = classDefinitionId;
	}

	public ClassAction() {
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}
	
	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}
	
	public void addAttribute(AttributeCondition attribute) {
		attributes.add(attribute);
	}
	
	public void setAttributes(List<AttributeCondition> attributes) {
		this.attributes = attributes;
	}
	
	public List<AttributeCondition> getAttributes(){
		return attributes;
	}

}
