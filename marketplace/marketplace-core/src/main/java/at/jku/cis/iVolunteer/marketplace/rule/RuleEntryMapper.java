package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.entities.AttributeConditionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.ClassConditionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.GeneralConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;

@Service
public class RuleEntryMapper{
	
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private ClassDefinitionService classDefinitionService;
	
	public GeneralConditionDTO toTarget(GeneralCondition source, String tenantId) {
		PropertyDefinition<Object> propertyDefinition = (PropertyDefinition<Object>) propertyDefinitionRepository
				                       .getByNameAndTenantId(source.getAttributeName(), tenantId).get(0);
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

	
	public ClassConditionDTO toTarget(ClassCondition source, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService
				             .getClassDefinitionById(source.getClassDefinitionId(), tenantId);
		ClassConditionDTO dto = new ClassConditionDTO(classDefinition, 
    										source.getValue(), (AggregationOperatorType) source.getOperatorType());    
		dto.setAttributeConditions(
				source.getAttributeConditions()
				.stream()
				.map(entry -> toTarget((AttributeCondition)entry, tenantId, source.getClassDefinitionId()))
				.collect(Collectors.toList())
				);
		return dto;
}

	public ClassCondition toSource (ClassConditionDTO target) {
		ClassCondition entry = new ClassCondition(target.getClassDefinition().getId(),
			                                      target.getValue(), 
			                                      target.getAggregationOperatorType());
		entry.setAttributeConditions(
				target
				.getAttributeConditions()
				.stream()
				.map(e -> toSource(e))
				.collect(Collectors.toList()));
		return entry;
	}

	public AttributeConditionDTO toTarget(AttributeCondition source, String tenantId, String classDefinitionId) {
		ClassDefinition classDefinition = classDefinitionService
				             .getClassDefinitionById(classDefinitionId, tenantId);
		ClassProperty<Object> classProperty = (ClassProperty<Object>) classDefinition.getProperties()
				                                  .stream()
				                                  .filter(entry -> entry.getId().equals(source.getClassPropertyId()));
		
		AttributeConditionDTO dto = new AttributeConditionDTO(classDefinition, 
				                            classProperty, source.getValue(), 
				                            (ComparisonOperatorType)source.getOperatorType());
		return dto;
}

	public AttributeCondition toSource (AttributeConditionDTO target) {
		System.out.println(" target - attribute condition: " + target.getClassProperty().getName());
		AttributeCondition entry = new AttributeCondition(target.getClassProperty().getId(),
			                                      target.getValue(), 
			                                      target.getComparisonOperatorType());
		return entry;
	}

	
	public ClassActionDTO toTarget(ClassAction source, String tenantId) {
		ClassDefinition classDefinition = classDefinitionService
	             .getClassDefinitionById(source.getClassDefinitionId(), tenantId);
		ClassActionDTO dto = new ClassActionDTO(source.getType(), classDefinition);
		dto.setAttributes(
				source.getAttributes()
				.stream()
				.map(entry -> toTarget(entry, tenantId, source.getClassDefinitionId()))
				.collect(Collectors.toList())
				); 
		return dto;
	}

	public ClassAction toSource (ClassActionDTO target) {
		ClassAction entry = new ClassAction(target.getClassDefinition().getId(),
			                                              target.getActionType());
		entry.setAttributes(
				target.getAttributes()
				.stream()
				.map(e -> toSource(e))
		        .collect(Collectors.toList())
				);
		return entry;
	}
	
}
