package at.jku.cis.iVolunteer.marketplace.rule.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.entities.GeneralConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class GeneralConditionMapper {
	
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	
	public GeneralConditionDTO toTarget(GeneralCondition source, String tenantId) {
		PropertyDefinition<Object> propertyDefinition = (PropertyDefinition<Object>) propertyDefinitionRepository
				                       .getByNameAndTenantId(source.getAttributeName(), tenantId);
		GeneralConditionDTO dto = new GeneralConditionDTO(propertyDefinition, 
				                                          source.getValue(),  
				                                          (ComparisonOperatorType) source.getOperatorType());
		return dto;
	}

	public GeneralCondition toSource(GeneralConditionDTO target) {
		GeneralCondition entry = new GeneralCondition(
				                       target.getPropertyDefinition().getName(), 
				                       target.getValue(), 
				                       target.getComparisonOperatorType());
		return entry;
	}

	public List<GeneralConditionDTO> toTargets(List<GeneralCondition> sources, String tenantId) {
		return sources.stream().map(source -> toTarget(source, tenantId)).collect(Collectors.toList());
	}

	public List<GeneralCondition> toSources(List<GeneralConditionDTO> targets) {
		return targets.stream().map(target -> toSource(target)).collect(Collectors.toList());
	}
}
