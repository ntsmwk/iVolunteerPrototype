package at.jku.cis.iVolunteer.marketplace.task.template;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@Repository
public interface UserDefinedTaskTemplateRepository extends HasTenantRepository<UserDefinedTaskTemplate, String> {

}
