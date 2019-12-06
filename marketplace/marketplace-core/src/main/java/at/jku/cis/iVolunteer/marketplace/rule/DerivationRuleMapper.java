package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.SourceRuleEntryDTO;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setMarketplaceId(dto.getMarketplaceId());
		dto.setName(dto.getName());
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
		return null;
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		// TODO Auto-generated method stub
		return null;
	}

}
