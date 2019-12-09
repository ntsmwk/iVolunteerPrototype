package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.SourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.SourceRuleEntryDTO;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setMarketplaceId(source.getMarketplaceId());
		dto.setName(source.getName());
		dto.setSources(source.getSources().stream()
				.map(entry -> new SourceRuleEntryDTO(classDefinitionRepository.findOne(entry.getClassDefinitionId()),
						entry.getMappingOperator()))
				.collect(Collectors.toList()));
		dto.setTargets(source.getTargets().stream().map(id -> classDefinitionRepository.findOne(id))
				.collect(Collectors.toList()));
		return dto;
	}

	@Override
	public List<DerivationRuleDTO> toTargets(List<DerivationRule> sources) {
		return sources.stream().map(source -> toTarget(source)).collect(Collectors.toList());
	}

	@Override
	public DerivationRule toSource(DerivationRuleDTO target) {
		DerivationRule derivationRule = new DerivationRule();
		derivationRule.setId(target.getId());
		derivationRule.setMarketplaceId(target.getMarketplaceId());
		derivationRule.setName(target.getName());
		derivationRule.setSources(target.getSources().stream()
				.map(e -> new SourceRuleEntry(e.getClassDefinition().getId(), e.getMappingOperator()))
				.collect(Collectors.toList()));

		derivationRule.setTargets(target.getTargets().stream().map(cd -> cd.getId()).collect(Collectors.toList()));
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

}
