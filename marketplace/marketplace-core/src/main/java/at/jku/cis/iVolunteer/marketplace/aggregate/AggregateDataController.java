package at.jku.cis.iVolunteer.marketplace.aggregate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.task.XTaskInstanceService;
import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model._mapper.xnet.XClassInstanceToTaskCertificateMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;

@RestController
public class AggregateDataController {

	@Autowired ClassInstanceService classInstanceService;
	@Autowired XTaskInstanceService xTaskInstanceService;
	@Autowired XClassInstanceToTaskCertificateMapper classInstanceToTaskCertificateMapper;
	private static final Logger logger = LoggerFactory.getLogger(AggregateDataController.class);

	@PutMapping("/aggregate/class-and-task-instance")
	private List<XTaskCertificate> getClassAndTaskInstancesForTenant(
			@RequestBody GetClassAndTaskInstancesRequest body) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetypeAndUserId(
				ClassArchetype.TASK, body.getUser().getId(), body.getTenant().getId());
		List<XTaskCertificate> certificates = mapToXTaskCertificate(body, classInstances);
		return certificates;
	}

	@PutMapping("/aggregate/class-and-task-instance/{year}")
	private List<XTaskCertificate> getClassAndTaskInstancesForTenantByYear(@PathVariable int year,
			@RequestBody GetClassAndTaskInstancesRequest body) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetypeAndUserId(
				ClassArchetype.TASK, body.getUser().getId(), body.getTenant().getId());
		classInstances = this.classInstanceService.filterTaskInstancesByYear(year, classInstances);

		List<XTaskCertificate> certificates = mapToXTaskCertificate(body, classInstances);
		return certificates;
	}

	private List<XTaskCertificate> mapToXTaskCertificate(GetClassAndTaskInstancesRequest body,
			List<ClassInstance> classInstances) {
		List<XTaskCertificate> certificates = new ArrayList<>();
		for (ClassInstance ci : classInstances) {
			TaskInstance ti = xTaskInstanceService.getTaskInstance(ci.getId());
			if (!body.isOnlyOpened() || (body.isOnlyOpened() && ti.getStatus().equals(TaskInstanceStatus.OPEN))) {
				certificates
						.add(classInstanceToTaskCertificateMapper.toTarget(ci, ti, body.getTenant(), body.getUser()));
			}
		}
		return certificates;
	}
}
