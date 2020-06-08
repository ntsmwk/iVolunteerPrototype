package at.jku.cis.iVolunteer.model.meta.core.enums;

public class EnumRelationship {
	
	String id;
	String sourceEnumEntryId;
	String targetEnumEntryId;
	
	public EnumRelationship() {
	
	}
	
	public EnumRelationship(String sourceEnumEntryId, String targetEnumEntryId) {
		this.sourceEnumEntryId = sourceEnumEntryId;
		this.targetEnumEntryId = targetEnumEntryId;
	}
	
	public EnumRelationship(EnumEntry sourceEnumEntry, EnumEntry targetEnumEntry) {
		this.sourceEnumEntryId = sourceEnumEntry.getId();
		this.targetEnumEntryId = targetEnumEntry.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
