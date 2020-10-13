package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.marketplace.rule.mapper.DerivationRuleMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;

@Service
public class DerivationRuleService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private DerivationRuleMapper derivationRuleMapper;
	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private RuleService ruleService;
		
	public List<DerivationRuleDTO> getRules(String tenantId) {
		List<DerivationRule> all = derivationRuleRepository.getByTenantId(tenantId);
		return derivationRuleMapper.toTargets(all);
	}
	
	public DerivationRuleDTO getRule(String id) {
		return derivationRuleMapper.toTarget(derivationRuleRepository.findOne(id));
	}
	
	public DerivationRuleDTO getRuleByContainerAndName(String tenantId, String container, String ruleName) {
		return derivationRuleMapper.toTarget(derivationRuleRepository.getByTenantIdAndContainerAndName(tenantId, container, ruleName));
	}

	public DerivationRuleDTO createRule(DerivationRuleDTO derivationRuleDTO) {
		DerivationRule derivationRule = derivationRuleMapper.toSource(derivationRuleDTO);
		derivationRuleRepository.save(derivationRule);
		ruleService.addRule(derivationRule);
		//ruleService.executeRulesForAllVolunteers(derivationRule.getTenantId(), derivationRule.getContainer());
		return derivationRuleMapper.toTarget(derivationRule);
	}
	
	public List<RuleExecution> testRule(DerivationRuleDTO derivationRuleDTO) {
	    DerivationRule derivationRule = derivationRuleMapper.toSource(derivationRuleDTO);
		return ruleService.testRule(derivationRule);
	}

	public void updateRule(String id, DerivationRuleDTO derivationRuleDTO) {
		DerivationRule derivationRule = derivationRuleMapper.toSource(derivationRuleDTO);
		ruleService.addRule(derivationRule);
		derivationRuleRepository.save(derivationRule);
	}
	
	public List<FlatPropertyDefinition<Object>> getGeneralProperties(String tenantId){
		List<FlatPropertyDefinition<Object>> properties = new ArrayList<FlatPropertyDefinition<Object>>();
		
		FlatPropertyDefinition<Object> pd = (FlatPropertyDefinition<Object>) propertyDefinitionRepository.getByNameAndTenantId("Alter", tenantId).get(0);
		properties.add(pd);
		
		return properties;
	}
	
}