package at.jku.cis.iVolunteer.marketplace.rule.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.Condition;
import at.jku.cis.iVolunteer.model.rule.entities.ClassConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;

@Component
public class ClassConditionMapper {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassPropertyService classPropertyService;
	@Autowired private AttributeConditionMapper attributeConditionMapper;

	public ClassConditionDTO toTarget(ClassCondition source, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService.getClassDefinitionById(source.getClassDefinitionId());
		ClassConditionDTO dto = new ClassConditionDTO(classDefinition, source.getValue(),
				(AggregationOperatorType) source.getOperatorType());
		if (source.getClassPropertyId() != null) {
			// is only set for aggregation operator SUM
			ClassProperty<Object> classProperty = classPropertyService.
					     getClassPropertyFromAllClassProperties(classDefinition.getId(), source.getClassPropertyId());
			dto.setClassProperty(classProperty);
		}
		dto.setAttributeConditions(
				attributeConditionMapper.toTargets(source.getAttributeConditions(), tenantId, classDefinition.getId()));
		return dto;
	}

	public ClassCondition toSource(ClassConditionDTO target) {
		ClassCondition entry = new ClassCondition(target.getClassDefinition().getId(), target.getValue(),
				target.getAggregationOperatorType());
		if (target.getClassProperty() != null) // only set for aggregation operator SUM
			entry.setClassPropertyId(target.getClassProperty().getId());
		entry.setAttributeConditions(attributeConditionMapper.toSources(target.getAttributeConditions()));
		return entry;
	}

	public List<ClassConditionDTO> toTargets(List<Condition> sources, String tenantId) {
		return sources.stream().map(source -> toTarget((ClassCondition) source, tenantId)).collect(Collectors.toList());
	}

	public List<Condition> toSources(List<ClassConditionDTO> targets) {
		return targets.stream().map(target -> toSource(target)).collect(Collectors.toList());
	}
}
