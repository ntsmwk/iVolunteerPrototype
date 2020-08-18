package at.jku.cis.iVolunteer.model.matching;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class MatchingEntityMappings {

	ClassDefinition classDefinition;
	String path;
	String pathDelimiter;
	
	List<MatchingCollectorEntry> entities;
	int numberOfProperties;
	int numberOfDefinitions;


	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}

	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathDelimiter() {
		return pathDelimiter;
	}

	public void setPathDelimiter(String pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
	}

	public List<MatchingCollectorEntry> getEntities() {
		return entities;
	}

	public void setEntities(List<MatchingCollectorEntry> entities) {
		this.entities = entities;
	}

	public int getNumberOfProperties() {
		return numberOfProperties;
	}

	public void setNumberOfProperties(int numberOfProperties) {
		this.numberOfProperties = numberOfProperties;
	}

	public int getNumberOfDefinitions() {
		return numberOfDefinitions;
	}

	public void setNumberOfDefinitions(int numberOfDefinitions) {
		this.numberOfDefinitions = numberOfDefinitions;
	}
	
	

	

}
