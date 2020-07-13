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
public class ClassActionMapper {
	
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private AttributeConditionMapper attributeConditionMapper;
	
	public ClassActionDTO toTarget(ClassAction source, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService
	             .getClassDefinitionById(source.getClassDefinitionId(), tenantId);
		ClassActionDTO dto = new ClassActionDTO(source.getType(), classDefinition);
		dto.setAttributes(attributeConditionMapper.
				toTargets(source.getAttributes(), tenantId, classDefinition.getId()));
		return dto;
	}

	public ClassAction toSource (ClassActionDTO target) {
		ClassAction entry = new ClassAction(target.getClassDefinition().getId(),
			                                              target.getActionType());
		entry.setAttributes(attributeConditionMapper.toSources(target.getAttributes()));
		return entry;
	}
	
	public List<ClassActionDTO> toTargets(List<ClassAction> sources, String tenantId) {
		return sources.stream().map(source -> toTarget(source, tenantId)).collect(Collectors.toList());
	}

	public List<ClassAction> toSources(List<ClassActionDTO> targets) {
		return targets.stream().map(target -> toSource(target)).collect(Collectors.toList());
	}
}
