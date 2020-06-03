package at.jku.cis.iVolunteer.marketplace.configurations.enums;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.enums.EnumConfiguration;

public interface EnumConfigurationRepository extends HasTenantRepository<EnumConfiguration, String> {

	public List<EnumConfiguration> findByName(String name);

}
