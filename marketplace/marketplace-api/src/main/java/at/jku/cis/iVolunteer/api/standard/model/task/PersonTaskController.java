package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;

@RestController
@RequestMapping("/api/standard/PersonTasks")
public class PersonTaskController {
	
	private static final String FFEIDENBERG = "FF Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "MV Schwertberg";
	private static final String RKWILHERING = "RK Wilhering";
	
	@Autowired private PersonTaskService personTaskService;
	@Autowired private CoreTenantRestClient coreTenantRestClient;

	@PutMapping("/1")
	public void savePersonTask(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		personTaskService.savePersonTasks(tasks, tenantId);
	}
	

	@PutMapping("/2")
	public void savePersonTask2(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		personTaskService.savePersonTasks(tasks, tenantId);
	}
	
	@PutMapping("/3")
	public void savePersonTask3(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(MUSIKVEREINSCHWERTBERG);
		personTaskService.savePersonTasks(tasks, tenantId);
	}
	
}