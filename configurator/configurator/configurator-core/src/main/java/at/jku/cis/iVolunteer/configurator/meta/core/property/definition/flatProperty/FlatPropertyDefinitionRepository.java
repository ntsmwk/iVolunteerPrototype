package at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Repository
public interface FlatPropertyDefinitionRepository extends MongoRepository<FlatPropertyDefinition<Object>, String> {

	List<FlatPropertyDefinition<Object>> getByName(String name);
	
	List<FlatPropertyDefinition<Object>> getByNameAndTenantId(String name, String tenantId);

	List<FlatPropertyDefinition<Object>> getByTenantId(String tenantId);

	FlatPropertyDefinition<Object> getByIdAndTenantId(String id, String tenantId);
	
	List<FlatPropertyDefinition<Object>> getByIdAndTenantId(List<String> propertyIds, String tenantId);

	List<FlatPropertyDefinition<Object>> getAllByTenantId(String tenantId);
	
	

}
