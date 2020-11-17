package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

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
		if (treePropertyDefinition == null) {
			return null;
		}
		return treePropertyDefinitionRepository.save(treePropertyDefinition);
	}

	public void deleteTreePropertyDefinition(String id, boolean ignoreDeleteProtect) {
		TreePropertyDefinition pd = treePropertyDefinitionRepository.findOne(id);
		if (pd.isCustom() || ignoreDeleteProtect) {
			treePropertyDefinitionRepository.delete(id);
		}
	}

}
