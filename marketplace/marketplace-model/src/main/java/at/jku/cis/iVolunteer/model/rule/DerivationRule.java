package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class DerivationRule extends IVolunteerObject {

	private String name;
	private String container;
	private boolean active;
	private int fireNumOfTimes;
	private List<GeneralCondition> generalConditions = new ArrayList<GeneralCondition>();
	private List<Condition> conditions = new ArrayList<>();
	private List<ClassAction> actions = new ArrayList<>();
	private String containerRuleEntryId;

	public DerivationRule() {
		this.active = false;
		containerRuleEntryId = null;
	}
	
	public DerivationRule(String name, String container) {
		this();
		this.name = name;
		this.container = container;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public List<GeneralCondition> getGeneralConditions() {
		return generalConditions;
	}

	public void setGeneralConditions(List<GeneralCondition> generalConditions) {
		this.generalConditions = generalConditions;
	}

	public void addGeneralCondition(GeneralCondition generalCondition) {
		generalConditions.add(generalCondition);
	}
	
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public List<ClassAction> getActions() {
		return actions;
	}

	public void setActions(List<ClassAction> actions) {
		this.actions = actions;
	}
	
	public void addAction(ClassAction action) {
		actions.add(action);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public boolean isActivated() {
		return active;
	}
	
	public void setContainerRuleEntryId(String containerRuleEntryId) {
		this.containerRuleEntryId = containerRuleEntryId;
	}
	
	public String getContainerRuleEntryId() {
		return containerRuleEntryId;
	}
	
	public void setFireNumOfTimes(int fireNumOfTimes) {
		this.fireNumOfTimes = fireNumOfTimes;
	}

	public int getFireNumOfTimes() {
		return fireNumOfTimes;
	}
}