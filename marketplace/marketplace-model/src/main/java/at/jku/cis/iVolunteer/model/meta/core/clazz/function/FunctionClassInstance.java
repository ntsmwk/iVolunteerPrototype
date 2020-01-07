package at.jku.cis.iVolunteer.model.meta.core.clazz.function;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;


@Document(collection="classInstance")
public class FunctionClassInstance extends ClassInstance {

	public FunctionClassInstance() {
		this.setClassArchetype(ClassArchetype.FUNCTION);
	}

}
