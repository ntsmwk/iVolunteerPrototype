package at.jku.cis.iVolunteer.model.rule;

public abstract class Action {
	
	public enum ActionType {
		NEW, UPDATE, DELETE
	}
	
	public Action() {
		
	}
	
	private ActionType type;
	
	public Action(ActionType type) {
		this.type = type;
	}
	
	public ActionType getType() {
		return type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}

}
