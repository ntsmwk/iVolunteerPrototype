package at.jku.cis.iVolunteer.model.rule.archive;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.rule.operator.ActionType;

public class ClassTargetRuleEntryDTO extends TargetRuleEntryDTO {

	private List<TargetRuleEntryDTO> attributes;

	public ClassTargetRuleEntryDTO() {
		
	}
	
	public ClassTargetRuleEntryDTO(String classDefinitionId, ActionType actionType) {
		super(classDefinitionId, actionType);
		attributes= new ArrayList<>();
	}
	
	public List<TargetRuleEntryDTO> getAttributes(){
		return attributes;
	}
	
	public void setAttributes(List<TargetRuleEntryDTO> attributes){
		this.attributes = attributes;
	}
	
}