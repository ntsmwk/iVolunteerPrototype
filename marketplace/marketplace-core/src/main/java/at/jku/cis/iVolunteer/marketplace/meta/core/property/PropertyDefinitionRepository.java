package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Repository
public interface PropertyDefinitionRepository extends HasTenantRepository<PropertyDefinition<Object>, String> {

	PropertyDefinition<Object> getByNameAndTenantId(String name, String tenantId);
	
	List<PropertyDefinition<Object>> getByNameAndTenantId(List<String> names, String tenantId);

	PropertyDefinition<Object> getByIdAndTenantId(String id, String tenantId);
	
	List<PropertyDefinition<Object>> getByIdAndTenantId(List<String> propertyIds, String tenantId);

	List<PropertyDefinition<Object>> getAllByTenantId(String tenantId);
	
	

}
