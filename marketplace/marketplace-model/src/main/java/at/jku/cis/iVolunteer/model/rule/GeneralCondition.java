package at.jku.cis.iVolunteer.model.rule;

import at.jku.cis.iVolunteer.model.rule.operator.OperatorType;

public class GeneralCondition extends Condition {
	
	public enum Attribute {
		
		AGE("Alter");
			
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
	
	public GeneralCondition(Attribute attribute, Object value, OperatorType operator) {
		super(operator);
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
