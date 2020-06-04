package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class DerivationRule extends IVolunteerObject {

	private String name;
	private String container;
	private List<GeneralCondition> generalConditions;
	private List<Condition> conditions = new ArrayList<>();
	private List<Action> actions = new ArrayList<>();

	public DerivationRule() {
		generalConditions = new ArrayList<GeneralCondition>();
		conditions = new ArrayList<>();
		actions = new ArrayList<>();
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
	
	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}

}