package at.jku.cis.iVolunteer.marketplace.task;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import java.lang.String;
import java.util.List;

@Repository
public interface XTaskInstanceRepository extends HasTenantRepository<TaskInstance, String> {

	List<TaskInstance> findByIssuerId(String issuerid);
	
}
