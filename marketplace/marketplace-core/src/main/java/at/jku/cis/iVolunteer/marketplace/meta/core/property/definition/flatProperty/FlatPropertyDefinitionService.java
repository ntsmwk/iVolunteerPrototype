package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Service
public class FlatPropertyDefinitionService {

	@Autowired FlatPropertyDefinitionRepository propertyDefinitionRepository;

	public List<FlatPropertyDefinition<Object>> getAllPropertyDefinitions(String tenantId) {
		return propertyDefinitionRepository.findByTenantId(tenantId);
	}

	public FlatPropertyDefinition<Object> getPropertyDefinitionById(String id, String tenantId) {
		FlatPropertyDefinition<Object> findOne = propertyDefinitionRepository.getByIdAndTenantId(id, tenantId);
		return findOne;
	}

	public List<FlatPropertyDefinition<Object>> addPropertyDefinition(List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		if (propertyDefinitions == null) {
			return null;
		}
		return propertyDefinitionRepository.save(propertyDefinitions);
	}

	public void deletePropertyDefinition(String id, boolean ignoreDeleteProtect) {
		FlatPropertyDefinition<Object> pd = propertyDefinitionRepository.findOne(id);
		if (pd.isCustom() || ignoreDeleteProtect) {
			propertyDefinitionRepository.delete(id);
		}
	}

}
