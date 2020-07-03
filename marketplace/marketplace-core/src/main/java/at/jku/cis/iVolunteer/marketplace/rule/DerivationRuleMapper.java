package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired
	private RuleEntryMapper ruleEntryMapper;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		// @formatter:off
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setTenantId(source.getTenantId());
		dto.setMarketplaceId(source.getMarketplaceId());
		dto.setName(source.getName());
		dto.setContainer(source.getContainer());
		dto.setGeneralConditions(
				source
				 .getGeneralConditions()
				 .stream()
				 .map(entry -> ruleEntryMapper.toTarget(entry, source.getTenantId()))
				 .collect(Collectors.toList()));
		dto.setClassActions(
			source
				.getActions()
				.stream()
				.map(entry -> ruleEntryMapper.toTarget((ClassAction)entry, source.getTenantId()))
				.collect(Collectors.toList()));
		return dto;		 
		// @formatter:on
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
		derivationRule.setTenantId(target.getTenantId());
		derivationRule.setContainer(target.getContainer());
		derivationRule.setGeneralConditions(target.getGeneralConditions().stream().map(e -> ruleEntryMapper.toSource(e))
				.collect(Collectors.toList()));
		if (target.getConditions().size() > 0) {
			MultipleConditions multipleCondition = new MultipleConditions(LogicalOperatorType.AND);
			multipleCondition.setConditions(
					target.getConditions().stream().map(e -> ruleEntryMapper.toSource(e)).collect(Collectors.toList()));
			derivationRule.addCondition(multipleCondition);
		}
		derivationRule.setActions(target.getClassActions().stream()
				.map(e -> ruleEntryMapper.toSource((ClassActionDTO) e)).collect(Collectors.toList()));
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

}
