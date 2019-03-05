package at.jku.cis.iVolunteer.model.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BooleanProperty extends Property<Boolean> {
	
//	@Id
//	String id = super.id;
//	
//	String name;
//	boolean value;
//	
//	boolean defaultValue;
//	
//	List<ListEntry<Boolean>> legalValues;
//	List<Rule> rules;
//	
//	PropertyKind kind;
	
	public BooleanProperty() {
		kind = PropertyKind.BOOL;
	}
	
//	@Override
//	public String getId() {
//		return id;
//	}
//
//	@Override
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	@Override
//	public String getName() {
//		return name;
//	}
//
//	@Override
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	@Override
//	public Boolean getValue() {
//		return value;
//	}
//
//	@Override
//	public void setValue(Boolean value) {
//		this.value = value;
//	}
//	
//
//	@Override
//	public Boolean getDefaultValue() {
//		return defaultValue;
//	}
//	
//	@Override
//	public void setValues(List<ListEntry<Boolean>> value) {
//		//TODO
//	}
//	
//
//	@Override
//	public List<ListEntry<Boolean>> getValues() {
//		// TODO 
//		return null;
//	}
//
//	@Override
//	public void setDefaultValue(Boolean defaultValue) {
//		this.defaultValue = defaultValue;
//	}
//
//	@Override
//	public List<ListEntry<Boolean>> getLegalValues() {
//		return legalValues;
//	}
//	
//	@Override
//	public void setLegalValues(List<ListEntry<Boolean>> legalValues) {
//		this.legalValues = legalValues;
//		
//	}
//	
//	@Override
//	public PropertyKind getKind() {
//		return kind;
//	}
//	@Override
//	public void setKind(PropertyKind kind) {
//		this.kind = kind;
//	}
//	
//	@Override
//	public List<Rule> getRules() {
//		return rules;
//	}
//	
//	@Override
//	public void setRules(List<Rule> rules) {
//		this.rules = rules;
//	}
	

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BooleanProperty)) {
			return false;
		}
		return ((BooleanProperty) obj).id.equals(id);
	}

	

}
