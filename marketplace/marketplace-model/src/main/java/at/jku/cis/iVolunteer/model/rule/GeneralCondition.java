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
	
	private String attributeName;
	private Object value;
	
	public GeneralCondition(String attributeName, Object value, OperatorType operator) {
		super(operator);
		this.attributeName = attributeName;
		this.value = value;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

}
