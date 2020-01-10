package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.SourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.SourceRuleEntryDTO;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassPropertyService classPropertyService;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		// @formatter:off
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setMarketplaceId(source.getMarketplaceId());
		dto.setName(source.getName());
		dto.setSources(
			source
				.getSources()
				.stream()
				.map(entry -> new SourceRuleEntryDTO(
						classDefinitionRepository.findOne(entry.getClassDefinitionId()),
						classPropertyService.getClassPropertyById(entry.getClassDefinitionId(), entry.getClassPropertyId()),
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
		derivationRule.setSources(
			target
				.getSources()
				.stream()
				.map(e -> new AttributeSourceRuleEntry(
						e.getClassDefinition().getId(), 
						e.getClassProperty().getId(),
						e.getMappingOperatorType(),
						e.getValue(),
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
