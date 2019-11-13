package at.jku.cis.iVolunteer.model.meta.core.class_;

public enum ClassArchetype {

	COMPETENCE("COMPETENCE"), TASK("TASK"), FUNCTION("FUNCTION"), ACHIEVEMENT("ACHIEVEMENT") ;
	
	private final String archetype;
	
	private ClassArchetype(String archetype) {
		this.archetype = archetype;
	}
	
	public String getArchetype() {
		return this.archetype;
	}
}
