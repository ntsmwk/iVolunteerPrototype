package at.jku.cis.iVolunteer.marketplace.configurations.clazz;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;

public interface ClassConfigurationRepository extends HasTenantRepository<ClassConfiguration, String> {

	public List<ClassConfiguration> findByName(String name);

	@Query(value = "{}")
	public List<ClassConfiguration> findAllWithSort(Sort sort);
}
