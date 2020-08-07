package at.jku.cis.iVolunteer.marketplace.configurations.enums;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.enums.EnumDefinition;

public interface EnumDefinitionRepository extends HasTenantRepository<EnumDefinition, String> {

	public EnumDefinition findByName(String name);

}
