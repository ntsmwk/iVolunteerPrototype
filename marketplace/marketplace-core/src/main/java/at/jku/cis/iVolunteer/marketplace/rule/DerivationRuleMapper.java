package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassPropertyService classPropertyService;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		// @formatter:off
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setTenantId(source.getTenantId());
		dto.setMarketplaceId(source.getMarketplaceId());
		dto.setName(source.getName());
		dto.setAttributeSourceRules(
			source
				.getAttributeSourceRules()
				.stream()
				.map(entry -> new AttributeSourceRuleEntryDTO(
						classDefinitionRepository.findOne(entry.getClassDefinitionId()),
						// TODO Philipp: surce.getTenantId!???
						classPropertyService.getClassPropertyById(entry.getClassDefinitionId(), entry.getClassPropertyId()),
						entry.getMappingOperatorType(),
						entry.getValue(),
						entry.getAggregationOperatorType()))
				.collect(Collectors.toList()));
		
		dto.setClassSourceRules(
			source
				.getClassSourceRules()
				.stream()
				.map(entry -> new ClassSourceRuleEntryDTO(
						classDefinitionRepository.findOne(entry.getClassDefinitionId()),
						entry.getMappingOperatorType(),
						entry.getValue(),
						entry.getAggregationOperatorType()))
				.collect(Collectors.toList()));
		
		dto.setTarget(classDefinitionRepository.findOne(source.getTarget()));
		return dto;		 
		// @formatter:on
	}

	@Override
	public List<DerivationRuleDTO> toTargets(List<DerivationRule> sources) {
		return sources.stream().map(source -> toTarget(source)).collect(Collectors.toList());
	}

	@Override
	public DerivationRule toSource(DerivationRuleDTO target) {
		// @formatter:off
		DerivationRule derivationRule = new DerivationRule();
		derivationRule.setId(target.getId());
		derivationRule.setMarketplaceId(target.getMarketplaceId());
		derivationRule.setName(target.getName());
		derivationRule.setAttributeSourceRules(
			target
				.getAttributeSourceRules()
				.stream()
				.map(e -> new AttributeSourceRuleEntry(
						e.getClassDefinition().getId(), 
						e.getValue(),
						e.getClassProperty().getId(),
						e.getMappingOperatorType(),
						e.getAggregationOperatorType()))
				.collect(Collectors.toList()));

		derivationRule.setClassSourceRules(
				target
					.getClassSourceRules()
					.stream()
					.map(e -> new ClassSourceRuleEntry(
							e.getClassDefinition().getId(), 
							e.getValue(),
							e.getMappingOperatorType(),
							e.getAggregationOperatorType()))
					.collect(Collectors.toList()));

		derivationRule.setTarget(target.getTarget().getId());
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;		 
		// @formatter:on
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

}
