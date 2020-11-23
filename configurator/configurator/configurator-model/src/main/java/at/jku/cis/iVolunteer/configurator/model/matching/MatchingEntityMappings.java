package at.jku.cis.iVolunteer.configurator.model.matching;

import java.util.ArrayList;
import java.util.List;

public class MatchingEntityMappings {

	String pathDelimiter;
	
	List<MatchingMappingEntry> entities = new ArrayList<>();
	int numberOfProperties;
	int numberOfDefinitions;


	public String getPathDelimiter() {
		return pathDelimiter;
	}

	public void setPathDelimiter(String pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
	}

	public List<MatchingMappingEntry> getEntities() {
		return entities;
	}

	public void setEntities(List<MatchingMappingEntry> entities) {
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
