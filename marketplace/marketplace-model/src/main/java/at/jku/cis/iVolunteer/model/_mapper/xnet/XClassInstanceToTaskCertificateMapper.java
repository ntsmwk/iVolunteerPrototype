package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;
import at.jku.cis.iVolunteer.model.user.User;

@Component
public class XClassInstanceToTaskCertificateMapper {

	@Autowired
	XTaskInstanceToTaskSerializedMapper taskInstanceToTaskSerializedMapper;
	@Autowired
	XTenantToTenantSerializedMapper tenantToTenantSerializedMapper;
	@Autowired
	XUserMapper userMapper;

	public XTaskCertificate toTarget(ClassInstance source, TaskInstance taskInstance, Tenant tenant, User user) {
		if (source == null) {
			return null;
		}

		XTaskCertificate taskCertificate = new XTaskCertificate();
		taskCertificate.setId(source.getId());
		taskCertificate.setTaskId(taskInstance != null ? taskInstance.getId() : null);
		taskCertificate.setTaskSerialized(taskInstanceToTaskSerializedMapper.toTarget(taskInstance));
		
//		Tenant tenant = tenantRestClient.getTenantById(source.getTenantId());
		taskCertificate.setTenantSerialized(tenantToTenantSerializedMapper.toTarget(tenant));

//		User user = userController.findUserById(source.getId());
		taskCertificate.setUser(userMapper.toTarget((CoreUser) user));

		return taskCertificate;
	}


}
