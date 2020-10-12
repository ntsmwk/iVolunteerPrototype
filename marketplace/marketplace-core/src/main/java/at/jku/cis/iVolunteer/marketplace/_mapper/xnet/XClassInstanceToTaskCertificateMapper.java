package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.user.UserController;
import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;
import at.jku.cis.iVolunteer.model.user.User;

public class XClassInstanceToTaskCertificateMapper implements OneWayMapper<ClassInstance, XTaskCertificate> {

	@Autowired
	XClassInstanceToTaskSerializedMapper classInstanceToTaskSerializedMapper;
	@Autowired
	XTenantToTenantSerializedMapper tenantToTenantSerializedMapper;
	@Autowired
	CoreTenantRestClient tenantRestClient;
	@Autowired
	UserController userController;
	@Autowired
	XUserMapper userMapper;

	@Override
	public XTaskCertificate toTarget(ClassInstance source) {
		if (source == null) {
			return null;
		}

		XTaskCertificate taskCertificate = new XTaskCertificate();
		taskCertificate.setId(source.getId());
		taskCertificate.setTaskId("todo");
		taskCertificate.setTaskSerialized(classInstanceToTaskSerializedMapper.toTarget(source));

		Tenant tenant = tenantRestClient.getTenantById(source.getTenantId());
		taskCertificate.setTenantSerialized(tenantToTenantSerializedMapper.toTarget(tenant));

		User user = userController.findUserById(source.getId());
		taskCertificate.setUser(userMapper.toTarget(user));

		return taskCertificate;
	}

	@Override
	public List<XTaskCertificate> toTargets(List<ClassInstance> sources) {
		if (sources == null) {
			return null;
		}
		List<XTaskCertificate> targets = new ArrayList<>();

		for (ClassInstance source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}

}
