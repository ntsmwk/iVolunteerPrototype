package at.jku.cis.iVolunteer.model.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NumberProperty extends Property<Integer> {
//	@Id
//	String id = super.id;
//	
//	String name;
//	int value;
//	
//	int defaultValue;
//	
//	List<Rule> rules;
//	
//	PropertyKind kind;
	
	public NumberProperty() {
		kind = PropertyKind.WHOLE_NUMBER;
		defaultValue = 0;
		value = defaultValue;
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
//	public Integer getValue() {
//		return value;
//	}
//
//	@Override
//	public void setValue(Integer value) {
//		this.value = value;
//	}
//	
//	@Override
//	public List<ListEntry<Integer>> getValues() {
//		return values;
//	}
//
//	@Override
//	public void setValues(List<ListEntry<Integer>> values) {
//		this.values = values;
//	}
//
//	@Override
//	public Integer getDefaultValue() {
//		return defaultValue;
//	}
//
//	@Override
//	public void setDefaultValue(Integer defaultValue) {
//		this.defaultValue = defaultValue;
//	}
//
//	@Override
//	public List<ListEntry<Integer>> getLegalValues() {
//		return legalValues;
//	}
//	
//	@Override
//	public void setLegalValues(List<ListEntry<Integer>> legalValues) {
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
		if (!(obj instanceof NumberProperty)) {
			return false;
		}
		return ((NumberProperty) obj).id.equals(id);
	}
	

}
