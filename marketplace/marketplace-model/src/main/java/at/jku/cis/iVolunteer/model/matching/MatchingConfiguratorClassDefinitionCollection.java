package at.jku.cis.iVolunteer.model.matching;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class MatchingConfiguratorClassDefinitionCollection {

	ClassDefinition collector;
	List<ClassDefinition> classDefinitions;
	int numberOfProperties;
	int numberOfDefinitions;


	public ClassDefinition getCollector() {
		return collector;
	}

	public void setCollector(ClassDefinition collector) {
		this.collector = collector;
	}

	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
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
