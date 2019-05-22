package at.jku.cis.iVolunteer.model.competence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.*;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;

@Document
public class Competence  extends ListEntry<String>{

//	@Id
//	private String id;
	private String name;

	public Competence() {
	}

	public Competence(String name) {
		this.name = name;
	}
	
//	public Competence(String id, String name) {
//		super(id, name);
//	}


	//@Override
	public String getId() {
		return id;
	}
	
	//@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//@Override
	public String getValue() {
		return this.getName();
	}
	
	//@Override
	public void setValue(String value) {
		this.setName(value);
	}
}
