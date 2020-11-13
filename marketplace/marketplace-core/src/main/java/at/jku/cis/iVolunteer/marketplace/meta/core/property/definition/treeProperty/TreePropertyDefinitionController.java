package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

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

import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyRelationship;

@Service
public class TreePropertyDefinitionController {

	@Autowired
	TreePropertyDefinitionRepository treePropertyDefinitionRepository;

	public List<TreePropertyDefinition> getAllTreePropertyDefinitions() {
		return treePropertyDefinitionRepository.findAll();
	}

	public List<TreePropertyDefinition> getAllTreePropertyDefinitionssForTenant(String tenantId) {
		return treePropertyDefinitionRepository.findByTenantId(tenantId);
	}

	public TreePropertyDefinition getTreePropertyDefinitionById(String id) {
		return treePropertyDefinitionRepository.findOne(id);
	}

	public TreePropertyDefinition getTreePropertyDefinitionByName(String name) {
		return treePropertyDefinitionRepository.getByName(name);
	}

	public List<TreePropertyDefinition> addTreePropertyDefinition(List<TreePropertyDefinition> treePropertyDefinition) {
		return treePropertyDefinitionRepository.save(treePropertyDefinition);
	}

	public void deleteTreePropertyDefinition(String id) {
		treePropertyDefinitionRepository.delete(id);
	}

}
