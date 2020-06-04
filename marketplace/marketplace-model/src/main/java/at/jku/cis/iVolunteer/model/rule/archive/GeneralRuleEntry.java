package at.jku.cis.iVolunteer.model.rule.archive;

public class GeneralRuleEntry extends SourceRuleEntry {
	
	public enum Attribute {
		AGE("Alter"), STATUS("Status");
		
		private String attrName;
		
		Attribute(String attrName) {
			this.attrName = attrName;
		}
		
		public String getName() {
			return attrName;
		}
	}

	public GeneralRuleEntry(String key, Object value, MappingOperatorType mappingOperatorType) {
		super(key, value, mappingOperatorType);
	}
	
	public Attribute getAttribute() {
		return Attribute.valueOf(getKey());
	}
	
	public void setAttribute(Attribute attribute) {
		setKey(attribute.getName());
	}
	
}
