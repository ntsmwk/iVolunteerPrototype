package at.jku.cis.iVolunteer.mapper.meta.core.class_;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassInstance;

@Component
public class ClassDefinitionToInstanceMapper implements OneWayMapper<ClassDefinition, ClassInstance> {

	@Autowired ClassPropertyToPropertyInstanceMapper classPropertyToPropertyInstanceMapper;

	@Override
	public ClassInstance toTarget(ClassDefinition source) {
		if (source == null) {
			return null;
		}
		ClassInstance classInstance = null;

		switch (source.getClassArchetype()) {
		case ACHIEVEMENT:
			classInstance = new AchievementClassInstance();
			break;
		case COMPETENCE:
			classInstance = new CompetenceClassInstance();
			break;
		case FUNCTION:
			classInstance = new FunctionClassInstance();
			break;
		case TASK:
			classInstance = new TaskClassInstance();
			break;
		default:
			break;

		}

		classInstance.setId(null);
		classInstance.setClassDefinitionId(source.getId());

		classInstance.setName(source.getName());

//		List<PropertyInstance<Object>> properties = new ArrayList<PropertyInstance<Object>>();
//		for (ClassProperty<Object> classProperty : source.getProperties()) {
//			properties.add(cPTPIMapper.toTarget(classProperty));
//		}
//		classInstance.setProperties(properties);

		return classInstance;
	}

	@Override
	public List<ClassInstance> toTargets(List<ClassDefinition> sources) {
		if (sources == null) {
			return null;
		}

		List<ClassInstance> instances = new ArrayList<ClassInstance>();
		for (ClassDefinition definition : sources) {
			instances.add(this.toTarget(definition));
		}

		return instances;
	}

}
