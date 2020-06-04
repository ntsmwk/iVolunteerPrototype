package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;

import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.archive.ClassSourceRuleEntryDTO;
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
		dto.setContainer(source.getContainer());
		/*dto.setGeneralConditions(
				source
				 .getGeneralConditions()
				 .stream()
				 .map(entry -> RuleEntryMapper.toTarget(entry))
				 .collect(Collectors.toList()));*/
		/*dto.setRhsActions(
			source
				.getRhsActions()
				.stream()
				.map(entry -> new ClassTargetRuleEntryDTO(
						classDefinitionRepository.findOne(entry.getKey()),
						entry.getActionType()))
				.collect(Collectors.toList()));
		
		dto.setTarget(classDefinitionRepository.findOne(source.getTarget())); */
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
		/*derivationRule.setLhsConditions(
				target
					.getLhsConditions()
					.stream()
					.map(e -> RuleEntryMapper.toSource(e))
					.collect(Collectors.toList()));

		derivationRule.setTarget(target.getTarget().getId()); */
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;		 
		// @formatter:on
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

	
	public static class RuleEntryMapper{
	
	public static SourceRuleEntryDTO toTarget(SourceRuleEntry source) {
		// @formatter:off
		if (source instanceof GeneralRuleEntry)
			return toTarget((GeneralRuleEntry)source);
		return toTarget((ClassSourceRuleEntry)source);
	}
	
	public static SourceRuleEntry toSource(SourceRuleEntryDTO target) {
		// @formatter:off
		if (target instanceof GeneralRuleEntryDTO)
			return toSource((GeneralRuleEntryDTO)target);
		return toSource((ClassSourceRuleEntryDTO)target);
	}
		
	/*public static GeneralRuleEntryDTO toTarget(GeneralRuleEntry source) {
		GeneralRuleEntryDTO dto = new GeneralRuleEntryDTO(source.getKey(), source.getValue(), (MappingOperatorType) source.getOperatorType());
	}
	
	public static GeneralRuleEntry toSource(GeneralRuleEntryDTO target) {
		GeneralRuleEntry entry = new GeneralRuleEntry(target.getKey(), target.getValue(), (MappingOperatorType)target.getOperatorType());
	}
	
	public static SourceRuleEntryDTO toTarget(ClassSourceRuleEntry source) {
	    ClassSourceRuleEntryDTO dto = new ClassSourceRuleEntryDTO(source.getKey(), 
	    										source.getValue(), source.getOperatorType());
		dto.setAttributeSourceRules(
				source
				 .getAttributeSourceRules()
				 .stream()
				 .map(entry -> new SourceRuleEntryDTO(
						                 entry.getKey(), 
						                 entry.getValue(), 
						                 entry.getOperatorType()))
				 .collect(Collectors.toList()));
	}
	
	public static ClassSourceRuleEntry toSource (ClassSourceRuleEntryDTO target) {
		ClassSourceRuleEntry entry = new ClassSourceRuleEntry(target.getKey(), 
				                                              target.getValue(), 
				                                              target.getOperatorType());
		entry.setAttributeSourceRules(
				target
				 .getAttributeSourceRules()
				 .stream()
				 .map(e -> new SourceRuleEntry(
						 				e.getKey(), 
						 				e.getValue(), 
						 				e.getOperatorType()))
				 .collect(Collectors.toList()));
	}
	
	public static ClassTargetRuleEntryDTO toTarget(ClassTargetRuleEntry source) {
	    ClassTargetRuleEntryDTO dto = new ClassTargetRuleEntryDTO(source.getKey(), source.getActionType());
		dto.setAttributes(
				source
				 .getAttributes()
				 .stream()
				 .map(entry -> new TargetRuleEntryDTO(
						            entry.getKey(), 
						            entry.getActionType))    
				 .collect(Collectors.toList()));
	}
	
	public static ClassTargetRuleEntry toSource (ClassTargetRuleEntryDTO target) {
		ClassTargetRuleEntry entry = new ClassTargetRuleEntry(target.getKey(),
				                                              target.getActionType());
		entry.setAttributes(
				target
				 .getAttributes()
				 .stream()
				 .map(e -> new RuleEntry(
						 				e.getKey(), 
						 				e.getValue()))
				 .collect(Collectors.toList()));
	}*/
		
	}
}
