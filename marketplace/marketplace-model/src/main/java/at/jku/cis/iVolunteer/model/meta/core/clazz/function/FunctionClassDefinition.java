package at.jku.cis.iVolunteer.model.meta.core.clazz.function;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document(collection = "classDefinition")
public class FunctionClassDefinition extends ClassDefinition {

	public FunctionClassDefinition() {
		this.setClassArchetype(ClassArchetype.FUNCTION);
	}

}
