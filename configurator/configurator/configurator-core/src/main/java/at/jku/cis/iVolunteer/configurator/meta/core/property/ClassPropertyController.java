package at.jku.cis.iVolunteer.configurator.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.model.meta.core.ClassPropertyRequestObject;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;

@RestController
public class ClassPropertyController {

	@Autowired private ClassPropertyService classPropertyService;

	@PutMapping("meta/core/property/class/get-classproperty-from-definition-by-id")
	private List<ClassProperty<Object>> getClassPropertyFromPropertyDefinitionById(
			@RequestBody ClassPropertyRequestObject requestObject) {
		return classPropertyService.getClassPropertyFromDefinitionById(requestObject.getFlatPropertyDefinitionIds(), requestObject.getTreePropertyDefinitionIds());
	}
}