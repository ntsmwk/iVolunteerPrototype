package at.jku.cis.iVolunteer.marketplace.fake.configuratorReset;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;

@Document
public class ClassesAndRelationshipsToReset {
	@Id String id;
	List<Relationship> relationships;

	
	List<ClassDefinition> classDefinitions;
	
	List<ClassConfiguration> configurators;

	
	public ClassesAndRelationshipsToReset() {
		relationships = new LinkedList<Relationship>();

		classDefinitions = new LinkedList<ClassDefinition>();
		
		configurators = new LinkedList<ClassConfiguration>();

	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public List<Relationship> getRelationships() {
		return relationships;
	}


	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}


	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}


	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}


	public List<ClassConfiguration> getConfigurators() {
		return configurators;
	}


	public void setConfigurators(List<ClassConfiguration> configurators) {
		this.configurators = configurators;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassesAndRelationshipsToReset)) {
			return false;
		}
		return ((ClassesAndRelationshipsToReset) obj).id.equals(id);
	}
	

	
	
	
	
	
}
