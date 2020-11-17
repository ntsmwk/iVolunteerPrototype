package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		if (!pd.isDeleteProtected() || ignoreDeleteProtect) {
			propertyDefinitionRepository.delete(id);
		}
	}

}
