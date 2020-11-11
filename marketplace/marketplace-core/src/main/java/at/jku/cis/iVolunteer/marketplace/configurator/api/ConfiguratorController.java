package at.jku.cis.iVolunteer.marketplace.configurator.api;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceController;
import at.jku.cis.iVolunteer.model._httprequests.configurator.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model._httprequests.configurator.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model._httprequests.configurator.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@RestController
public class ConfiguratorController {
	
	@Autowired private ClassInstanceController classInstanceController;
	
	@PostMapping("/response/class-instance-configurator")
	private ClassInstance classInstanceConfiguratorResponse(@RequestBody ClassInstanceConfiguratorResponseRequestBody req) {
		System.out.println("class instance configurator");
		System.out.println(req.getClassInstance().getId());
		System.out.println(req.getClassInstance().getName());
		return classInstanceController.createNewClassInstances(Collections.singletonList(req.getClassInstance())).get(0);
	}
	
	@PostMapping("/response/class-configurator")
	private void classConfiguratorResponse(@RequestBody ClassConfiguratorResponseRequestBody req) {
		System.out.println(req.getClassConfiguration().getId());
		System.out.println(req.getClassConfiguration().getName());
		System.out.println("class configurator");
	}
	
	@PostMapping("/response/matching-configurator")
	private void matchingConfiguratorResponse(@RequestBody MatchingConfiguratorResponseRequestBody req) {
		System.out.println(req.getMatchingConfiguration().getId());
		System.out.println(req.getMatchingConfiguration().getName());
		System.out.println("matching configurator");
	}

}
