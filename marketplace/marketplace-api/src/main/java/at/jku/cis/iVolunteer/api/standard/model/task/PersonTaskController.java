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

	// TODO PHilipp MWE: remove "level"
	private static final int MV = 3;
	private static final int FF_NEW = 2;
	private static final int FF_OLD = 1;
	
	private static final String FFEIDENBERG = "FF_Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "Musikverein_Schwertberg";
	private static final String RKWILHERING = "RK_Wilhering";
	
	@Autowired private PersonTaskService personTaskService;
	@Autowired private CoreTenantRestClient coreTenantRestClient;

	@PutMapping("/1")
	public void savePersonTask(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		personTaskService.savePersonTasks(tasks, FF_OLD, tenantId);
	}
	

	@PutMapping("/2")
	public void savePersonTask2(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(FFEIDENBERG);
		personTaskService.savePersonTasks(tasks, FF_NEW, tenantId);
	}
	
	@PutMapping("/3")
	public void savePersonTask3(@RequestBody List<PersonTask> tasks) {
		String tenantId = coreTenantRestClient.getTenantIdByName(MUSIKVEREINSCHWERTBERG);
		personTaskService.savePersonTasks(tasks, MV, tenantId);
	}
	
}