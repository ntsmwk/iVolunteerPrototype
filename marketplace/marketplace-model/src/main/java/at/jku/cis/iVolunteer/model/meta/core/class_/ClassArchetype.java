package at.jku.cis.iVolunteer.model.meta.core.class_;

public enum ClassArchetype {

	COMPETENCE("COMPETENCE"), TASK("TASK"), TASKTEMPLATE("TASKTEMPLATE");
	
	private final String archetype;
	
	private ClassArchetype(String archetype) {
		this.archetype = archetype;
	}
	
	public String getArchetype() {
		return this.archetype;
	}
}
