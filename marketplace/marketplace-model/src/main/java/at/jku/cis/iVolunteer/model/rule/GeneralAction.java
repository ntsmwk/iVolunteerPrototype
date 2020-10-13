package at.jku.cis.iVolunteer.model.rule;


public class GeneralAction extends Action {
	
	public enum Attribute {
		
		STATUS("Status");
			
		private String attrName;
			
		Attribute(String attrName) {
			this.attrName = attrName;
		}
			
		public String getName() {
			return attrName;
		}
	}
	
	private Attribute attribute;
	private Object value;
	
	public GeneralAction(ActionType type) {
		super(type);
	}
	
	public GeneralAction(ActionType type, Attribute attribute, Object value) {
		this(type);
		this.attribute = attribute;
		this.value = value;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
