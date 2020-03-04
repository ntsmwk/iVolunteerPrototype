package at.jku.cis.iVolunteer.model.matching;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class MatchingClassDefinitionCollection {

	ClassDefinition collector;
	List<MatchingClassDefinitionCollectionEntry> collectionEntries;
	int numberOfProperties;
	int numberOfDefinitions;


	public ClassDefinition getCollector() {
		return collector;
	}

	public void setCollector(ClassDefinition collector) {
		this.collector = collector;
	}

	

	public List<MatchingClassDefinitionCollectionEntry> getCollectionEntries() {
		return collectionEntries;
	}

	public void setCollectionEntries(List<MatchingClassDefinitionCollectionEntry> collectionEntries) {
		this.collectionEntries = collectionEntries;
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
