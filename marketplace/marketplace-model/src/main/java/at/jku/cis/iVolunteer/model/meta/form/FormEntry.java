package at.jku.cis.iVolunteer.model.meta.form;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;

public class FormEntry {

	String positionLevel;
	ClassDefinition classDefinition;
	
	
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public ClassDefinition getClassDefinition() {
		return classDefinition;
	}
	public void setClassDefinition(ClassDefinition classDefinition) {
		this.classDefinition = classDefinition;
	}

	

}
