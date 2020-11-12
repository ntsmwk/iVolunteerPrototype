package at.jku.cis.iVolunteer.marketplace.aggregate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.task.XTaskInstanceService;
import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model._mapper.xnet.XClassInstanceToTaskCertificateMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;

@RestController
public class AggregateDataController {

	@Autowired ClassInstanceService classInstanceService;
	@Autowired XTaskInstanceService xTaskInstanceService;
	@Autowired XClassInstanceToTaskCertificateMapper classInstanceToTaskCertificateMapper;

	@PutMapping("/aggregate/class-and-task-instance")
	private List<XTaskCertificate> getClassAndTaskInstancesForTenant(
			@RequestParam(value = "startYear", defaultValue = "0") int startYear,
			@RequestBody GetClassAndTaskInstancesRequest body) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetypeAndUserId(
				ClassArchetype.TASK, body.getUser().getId(), body.getTenant().getId());
		classInstances = this.classInstanceService.filterTaskInstancesByYear(startYear, classInstances);
		List<XTaskCertificate> certificates = mapToXTaskCertificate(body, classInstances);
		return certificates;
	}

	private List<XTaskCertificate> mapToXTaskCertificate(GetClassAndTaskInstancesRequest body,
			List<ClassInstance> classInstances) {
		List<XTaskCertificate> certificates = new ArrayList<>();
		for (ClassInstance ci : classInstances) {
			TaskInstance ti = xTaskInstanceService.getTaskInstance(ci.getId());
			certificates.add(classInstanceToTaskCertificateMapper.toTarget(ci, ti, body.getTenant(), body.getUser()));
		}
		return certificates;
	}
}
