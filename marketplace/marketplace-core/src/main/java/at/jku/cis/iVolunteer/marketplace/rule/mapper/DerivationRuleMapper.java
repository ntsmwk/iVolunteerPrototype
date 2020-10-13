package at.jku.cis.iVolunteer.marketplace.rule.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.rule.Condition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class DerivationRuleMapper implements AbstractMapper<DerivationRule, DerivationRuleDTO> {

	@Autowired
	private DerivationRuleRepository derivationRuleRepository;
	@Autowired
	private GeneralConditionMapper generalConditionMapper;
	@Autowired
	private ClassConditionMapper classConditionMapper;
	@Autowired
	private ClassActionMapper classActionMapper;

	@Override
	public DerivationRuleDTO toTarget(DerivationRule source) {
		DerivationRuleDTO dto = new DerivationRuleDTO();
		dto.setId(source.getId());
		dto.setTenantId(source.getTenantId());
		dto.setMarketplaceId(source.getMarketplaceId());
		dto.setName(source.getName());
		dto.setContainer(source.getContainer());
		dto.setActive(source.getActive());
		dto.setFireNumOfTimes(source.getFireNumOfTimes());
		dto.setGeneralConditions(generalConditionMapper.toTargets(source.getGeneralConditions(), source.getTenantId()));
		if (!source.getConditions().isEmpty()) {
			// only one multiple condition is set --> in frontend only conjunctions possible
			// so far
			MultipleConditions multiCond = (MultipleConditions) source.getConditions().get(0);
			dto.setConditions(classConditionMapper.toTargets(multiCond.getConditions(), source.getTenantId()));
		}
		dto.setClassActions(classActionMapper.toTargets(source.getActions(), source.getTenantId()));
		return dto;
	}

	@Override
	public List<DerivationRuleDTO> toTargets(List<DerivationRule> sources) {
		return sources.stream().map(source -> toTarget(source)).collect(Collectors.toList());
	}

	@Override
	public DerivationRule toSource(DerivationRuleDTO target) {
		DerivationRule derivationRule;
		if (target.getId() == null) {
			derivationRule = new DerivationRule();
		} else {
			derivationRule = derivationRuleRepository.findOne(target.getId());
		}
		derivationRule.setId(target.getId());
		derivationRule.setMarketplaceId(target.getMarketplaceId());
		derivationRule.setName(target.getName());
		derivationRule.setTenantId(target.getTenantId());
		derivationRule.setContainer(target.getContainer());
		derivationRule.setActive(target.getActive());
		derivationRule.setFireNumOfTimes(1);
		derivationRule.setGeneralConditions(generalConditionMapper.toSources(target.getGeneralConditions()));
		if (target.getConditions().size() > 0) {
			ArrayList<Condition> conditions = new ArrayList<Condition>();
			// we create a conjunction of all conditions --> might change in future if
			// disjunctions and negations possible
			MultipleConditions multipleCondition = new MultipleConditions(LogicalOperatorType.AND);
			multipleCondition.setConditions(classConditionMapper.toSources(target.getConditions()));
			conditions.add(multipleCondition);
			derivationRule.setConditions(conditions);
		}
		derivationRule.setActions(classActionMapper.toSources(target.getClassActions()));
		derivationRule.setTimestamp(target.getTimestamp());
		return derivationRule;
	}

	@Override
	public List<DerivationRule> toSources(List<DerivationRuleDTO> targets) {
		return targets.stream().map(t -> toSource(t)).collect(Collectors.toList());
	}

}
