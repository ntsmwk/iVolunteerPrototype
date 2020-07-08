package at.jku.cis.iVolunteer.marketplace.rule.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.AttributeConditionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.entities.GeneralConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class AttributeConditionMapper {
	
	@Autowired private ClassDefinitionService classDefinitionService;
	
	public AttributeConditionDTO toTarget(AttributeCondition source, String tenantId, String classDefinitionId) {
		ClassDefinition classDefinition = classDefinitionService
				             .getClassDefinitionById(classDefinitionId, tenantId);
		ClassProperty<Object> classProperty = (ClassProperty<Object>) classDefinition.getProperties()
				                                  .stream()
				                                  .filter(entry -> entry.getId().equals(source.getClassPropertyId())).findFirst().orElse(null);
		
		AttributeConditionDTO dto = new AttributeConditionDTO(classDefinition, 
				                            classProperty, source.getValue(), 
				                            (ComparisonOperatorType)source.getOperatorType());
		return dto;
}

	public AttributeCondition toSource (AttributeConditionDTO target) {
		AttributeCondition entry = new AttributeCondition(target.getClassProperty().getId(),
			                                      target.getValue(), 
			                                      target.getComparisonOperatorType());
		return entry;
	}

	public List<AttributeConditionDTO> toTargets(List<AttributeCondition> sources, String tenantId, String classDefinitionId) {
		return sources.stream().map(source -> toTarget(source, tenantId, classDefinitionId)).collect(Collectors.toList());
	}

	public List<AttributeCondition> toSources(List<AttributeConditionDTO> targets) {
		return targets.stream().map(target -> toSource(target)).collect(Collectors.toList());
	}
}