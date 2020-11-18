package at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty;

import java.util.UUID;

import org.bson.types.ObjectId;


public class TreePropertyRelationship {
	
	String id;
	String sourceId;
	String targetId;
	
	public TreePropertyRelationship() {
	
	}
	
	public TreePropertyRelationship(String sourceId, String targetId) {
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	public TreePropertyRelationship(String sourceId, String targetId, boolean generateId) {
		if (generateId) {
			this.id = UUID.randomUUID().toString();
		}
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	public TreePropertyRelationship(TreePropertyEntry sourceEntry, TreePropertyEntry targetEntry) {
		this.sourceId = sourceEntry.getId();
		this.targetId = targetEntry.getId();
	}
	
	public TreePropertyRelationship(TreePropertyEntry sourceEntry, TreePropertyEntry targetEntry, boolean generateId) {
		if (generateId) {
			this.id = new ObjectId().toHexString();
		}
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

	
	
	
	
	



	
}
