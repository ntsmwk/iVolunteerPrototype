package at.jku.cis.iVolunteer.model.meta.core.clazz.function;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.VolunteeringClassDefinition;

@Document(collection = "classDefinition")
public class FunctionClassDefinition extends VolunteeringClassDefinition {

	public FunctionClassDefinition() {
		this.setClassArchetype(ClassArchetype.FUNCTION);
	}

}
