package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Repository
public interface PropertyDefinitionRepository extends HasTenantRepository<PropertyDefinition<Object>, String> {

	// TODO Philipp tenantId?
	List<PropertyDefinition<Object>> findByName(String name);

	PropertyDefinition<Object> findById(String id, String tenantId);
	
	List<PropertyDefinition<Object>> findAllById(List<String> propertyIds, String tenantId);
	List<PropertyDefinition<Object>> findAllByTenantId(String tenantId);


}
