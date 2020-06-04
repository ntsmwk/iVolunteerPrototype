package at.jku.cis.iVolunteer.model.rule.archive;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class ClassSourceRuleEntryDTO extends SourceRuleEntryDTO {

	private List<SourceRuleEntryDTO> attributeSourceRules;

	public ClassSourceRuleEntryDTO() {
		
	}
	
	public ClassSourceRuleEntryDTO(String classDefinitionId, Object value, OperatorType operatorType) {
		super(classDefinitionId, value, operatorType);
		attributeSourceRules = new ArrayList<>();
	}
	
	public List<SourceRuleEntryDTO> getAttributeSourceRules(){
		return attributeSourceRules;
	}
	
	public void setAttributeSourceRules(List<SourceRuleEntryDTO> attributeSourceRules){
		this.attributeSourceRules = attributeSourceRules;
	}
}