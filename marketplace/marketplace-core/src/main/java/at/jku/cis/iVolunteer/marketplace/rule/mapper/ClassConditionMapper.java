package at.jku.cis.iVolunteer.marketplace.rule.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.Condition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.ClassConditionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.entities.GeneralConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class ClassConditionMapper {
	
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private AttributeConditionMapper attributeConditionMapper;
	
	public ClassConditionDTO toTarget(ClassCondition source, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService
				             .getClassDefinitionById(source.getClassDefinitionId(), tenantId);
		ClassConditionDTO dto = new ClassConditionDTO(classDefinition, 
    										source.getValue(), (AggregationOperatorType) source.getOperatorType());    
		dto.setAttributeConditions(attributeConditionMapper.
				toTargets(source.getAttributeConditions(), tenantId, classDefinition.getId()));
		return dto;
	}

	public ClassCondition toSource (ClassConditionDTO target) {
		ClassCondition entry = new ClassCondition(target.getClassDefinition().getId(),
			                                      target.getValue(), 
			                                      target.getAggregationOperatorType());
		entry.setAttributeConditions(attributeConditionMapper.
				toSources(target.getAttributeConditions()));
		return entry;
	}
	
	public List<ClassConditionDTO> toTargets(List<Condition> sources, String tenantId) {
		return sources.stream().map(source -> toTarget((ClassCondition)source, tenantId)).collect(Collectors.toList());
	}

	public List<Condition> toSources(List<ClassConditionDTO> targets) {
		return targets.stream().map(target -> toSource(target)).collect(Collectors.toList());
	}
}
