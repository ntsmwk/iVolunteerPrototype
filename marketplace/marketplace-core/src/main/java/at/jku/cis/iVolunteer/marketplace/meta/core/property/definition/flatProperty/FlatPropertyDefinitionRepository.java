package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty;

import java.util.List;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Repository
public interface FlatPropertyDefinitionRepository extends HasTenantRepository<FlatPropertyDefinition<Object>, String> {

	List<FlatPropertyDefinition<Object>> getByNameAndTenantId(String name, String tenantId);

	FlatPropertyDefinition<Object> getByIdAndTenantId(String id, String tenantId);
	
	List<FlatPropertyDefinition<Object>> getByIdAndTenantId(List<String> propertyIds, String tenantId);

	List<FlatPropertyDefinition<Object>> getAllByTenantId(String tenantId);
	
	

}
