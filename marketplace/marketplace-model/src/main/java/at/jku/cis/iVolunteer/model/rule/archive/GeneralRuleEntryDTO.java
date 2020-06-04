package at.jku.cis.iVolunteer.model.rule.archive;

import at.jku.cis.iVolunteer.model.rule.archive.GeneralRuleEntry.Attribute;

public class GeneralRuleEntryDTO extends SourceRuleEntryDTO {

	public GeneralRuleEntryDTO() {
		
	}
	
	public GeneralRuleEntryDTO(String key, Object value, MappingOperatorType mappingOperatorType) {
		super(key, value, mappingOperatorType);
	}
	
    public Attribute getAttribute() {
    	return Attribute.valueOf(getKey());
    }
    
    public void setAttribute(Attribute attribute) {
    	setKey(attribute.getName());
    }
	
}