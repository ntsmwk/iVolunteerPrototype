package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class DerivationRule extends IVolunteerObject {

	private String name;
	private List<ClassSourceRuleEntry> lhsClassConditions = new ArrayList<>();
	//private List<AttributeSourceRuleEntry> attributeSourceRuleEntries = new ArrayList<>();
	private List<GeneralAttributeEntry> lhsGeneralConditions = new ArrayList<>();
	private List<ClassActionRuleEntry> rhsRuleActions = new ArrayList<>();
	private String target;

	public DerivationRule() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<ClassSourceRuleEntry> getLhsClassConditions() {
		return lhsClassConditions;
	}

	public void setLhsClassConditions(List<ClassSourceRuleEntry> lhsClassConditions) {
		this.lhsClassConditions = lhsClassConditions;
	}

	public List<GeneralAttributeEntry> getLhsGeneralConditions() {
		return lhsGeneralConditions;
	}

	public void setLhsGeneralConditions(List<GeneralAttributeEntry> lhsGeneralConditions) {
		this.lhsGeneralConditions = lhsGeneralConditions;
	}
	
	public List<ClassActionRuleEntry> getRhsRuleActions() {
		return rhsRuleActions;
	}

	public void setRhsRuleActions(List<ClassActionRuleEntry> rhsRuleActions) {
		this.rhsRuleActions = rhsRuleActions;
	}

}