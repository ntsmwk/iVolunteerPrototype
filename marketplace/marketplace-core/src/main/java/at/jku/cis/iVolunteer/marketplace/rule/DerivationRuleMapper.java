package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.entities.GeneralConditionDTO;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;
/*import at.jku.cis.iVolunteer.model.rule.archive.ClassSourceRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.ClassTargetRuleEntry;
import at.jku.cis.iVolunteer.model.rule.archive.ClassTargetRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.GeneralRuleEntry;
import at.jku.cis.iVolunteer.model.rule.archive.GeneralRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.RuleEntry;
import at.jku.cis.iVolunteer.model.rule.archive.RuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.SourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.archive.SourceRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.TargetRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.GeneralRuleEntry.Attribute;
import at.jku.cis.iVolunteer.model.rule.condition.ClassSourceRuleEntry;
*/
@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassPropertyService classPropertyService;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private RuleEntryMapper ruleEntryMapper;


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
		dto.setActions(
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
		System.out.println(" derivation rule: " + target.getName());
		System.out.println(" generalCond: " + target.getGeneralConditions().size());
		System.out.println(" conditions: " + target.getConditions().size());
		System.out.println(" actions: " + target.getClassActions());
		
		DerivationRule derivationRule = new DerivationRule();
		derivationRule.setId(target.getId());
		derivationRule.setMarketplaceId(target.getMarketplaceId());
		derivationRule.setName(target.getName());
		derivationRule.setTenantId(target.getTenantId());
		derivationRule.setContainer(target.getContainer());
		derivationRule.setGeneralConditions(
				target
					.getGeneralConditions()
					.stream()
					.map(e -> ruleEntryMapper.toSource(e))
					.collect(Collectors.toList()));
		if (target.getConditions().size() > 0) {
			MultipleConditions multipleCondition = new MultipleConditions(LogicalOperatorType.AND);
			multipleCondition.setConditions(
					target
						.getConditions()
						.stream()
						.map(e -> ruleEntryMapper.toSource(e))
						.collect(Collectors.toList()));
			derivationRule.addCondition(multipleCondition);
		} 
		derivationRule.setActions(
				target
				   .getClassActions()
				   .stream() 
				   .map(e -> ruleEntryMapper.toSource((ClassActionDTO)e))
				   .collect(Collectors.toList()));	  
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;		 
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

}
