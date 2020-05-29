package at.jku.cis.iVolunteer.model.rule;

public class GeneralAttributeEntry {
	
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
	private MappingOperatorType mappingOperatorType;
	
	public GeneralAttributeEntry(Attribute attribute, Object value, MappingOperatorType mappingOperatorType) {
		this.attribute = attribute;
		this.value = value;
		this.mappingOperatorType = mappingOperatorType;
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
	
	public MappingOperatorType getMappingOperatorType() {
		return mappingOperatorType;
	}
	
	public void setMappingOperatorType(MappingOperatorType mappingOperatorType) {
		this.mappingOperatorType = mappingOperatorType;
	}
}
