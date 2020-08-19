package at.jku.cis.iVolunteer.model.matching;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class MatchingMappingEntry {

	ClassDefinition classDefinition;
	String path;
	String pathDelimiter;

	String sourceRelationshipId;
	String targetRelationshipId;

	public MatchingMappingEntry() {

	}

	public MatchingMappingEntry(ClassDefinition classDefinition, String path, String pathDelimiter) {
		this.classDefinition = classDefinition;
		this.path = path;
		this.pathDelimiter = pathDelimiter;
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

	public String getPathDelimiter() {
		return pathDelimiter;
	}

	public void setPathDelimiter(String pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
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
