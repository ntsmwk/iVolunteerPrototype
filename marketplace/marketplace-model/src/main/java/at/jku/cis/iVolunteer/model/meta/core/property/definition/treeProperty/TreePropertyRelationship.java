package at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty;

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



	
}
