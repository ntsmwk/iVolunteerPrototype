package at.jku.cis.iVolunteer.marketplace.configurations.enums;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.configurations.enums.EnumConfiguration;

public interface EnumConfigurationRepository extends HasTenantRepository<EnumConfiguration, String> {

	public EnumConfiguration findByName(String name);

}
