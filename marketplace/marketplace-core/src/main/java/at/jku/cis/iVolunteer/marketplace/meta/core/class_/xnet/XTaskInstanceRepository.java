package at.jku.cis.iVolunteer.marketplace.meta.core.class_.xnet;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;

@Repository
public interface XTaskInstanceRepository extends HasTenantRepository<TaskInstance, String> {

}
