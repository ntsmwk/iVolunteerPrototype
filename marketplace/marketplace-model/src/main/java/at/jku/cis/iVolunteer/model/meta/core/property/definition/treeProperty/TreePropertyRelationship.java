package at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty;

public class TreePropertyRelationship {
	
	String id;
	String sourceId;
	String targetId;
	
	String sourceEnumEntryId;
	String targetEnumEntryId;
	
	public TreePropertyRelationship() {
	
	}
	
	public TreePropertyRelationship(String sourceId, String targetId) {
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	public TreePropertyRelationship(TreePropertyEntry sourceEntry, TreePropertyEntry targetEntry) {
		this.sourceId = sourceEntry.getId();
		this.targetId = targetEntry.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getSourceEnumEntryId() {
		return sourceEnumEntryId;
	}

	public void setSourceEnumEntryId(String sourceEnumEntryId) {
		this.sourceEnumEntryId = sourceEnumEntryId;
	}

	public String getTargetEnumEntryId() {
		return targetEnumEntryId;
	}

	public void setTargetEnumEntryId(String targetEnumEntryId) {
		this.targetEnumEntryId = targetEnumEntryId;
	}
	
	
	
	
	



	
}
