package at.jku.cis.iVolunteer.model.matching;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class MatchingClassDefinitionCollectionEntry {
	
	String path;
	ClassDefinition classDefinition;
	
	String sourceRelationshipId;
	String targetRelationshipId;
	
	public MatchingClassDefinitionCollectionEntry() {
		
	}
	
	public MatchingClassDefinitionCollectionEntry(ClassDefinition classDefinition, String path) {
		this.classDefinition = classDefinition;
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}
	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	public String getSourceRelationshipId() {
		return sourceRelationshipId;
	}

	public void setSourceRelationshipId(String sourceRelationshipId) {
		this.sourceRelationshipId = sourceRelationshipId;
	}

	public String getTargetRelationshipId() {
		return targetRelationshipId;
	}

	public void setTargetRelationshipId(String targetRelationshipId) {
		this.targetRelationshipId = targetRelationshipId;
	}
	
	
	
	
}
