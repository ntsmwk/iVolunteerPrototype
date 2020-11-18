package at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public interface TreePropertyDefinitionRepository extends MongoRepository<TreePropertyDefinition, String> {

	public TreePropertyDefinition findByName(String name);

	public List<TreePropertyDefinition> findByTenantId(String tenantId);
	
	public List<TreePropertyDefinition> getByTenantId(String tenantId);
	
	public List<FlatPropertyDefinition<Object>> getByNameAndTenantId(String name, String tenantId);

}
