package at.jku.cis.iVolunteer.marketplace.rule;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.RuleService;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
import at.jku.cis.iVolunteer.model.rule.entities.ClassActionDTO;
import at.jku.cis.iVolunteer.model.rule.entities.DerivationRuleDTO;

@Service
public class DerivationRuleService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;
	@Autowired private DerivationRuleMapper derivationRuleMapper;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private RuleService ruleService;
		
	public List<DerivationRuleDTO> getRules(String tenantId) {
		List<DerivationRule> all = derivationRuleRepository.getByTenantId(tenantId);
		return derivationRuleMapper.toTargets(all);
	}
	
	public DerivationRuleDTO getRule(String id, String tenantId) {
		return derivationRuleMapper.toTarget(derivationRuleRepository.getByIdAndTenantId(id, tenantId));
	}

	public void createRule(DerivationRuleDTO derivationRuleDTO) {
		DerivationRule derivationRule = derivationRuleMapper.toSource(derivationRuleDTO);
		
		derivationRuleRepository.save(derivationRule);
		ruleService.addRule(derivationRule);
		
		ruleService.executeRulesForAllVolunteers(derivationRule.getTenantId(), derivationRule.getContainer());
	}
	
	public List<RuleExecution> testRule(DerivationRuleDTO derivationRuleDTO) {
		// reset possible actions --> we are only testing the conditions!
		derivationRuleDTO.setClassActions(new ArrayList<ClassActionDTO>());
		
	    DerivationRule derivationRule = derivationRuleMapper.toSource(derivationRuleDTO);
		
		derivationRule.setContainer(derivationRule.getContainer());
		ruleService.addRule(derivationRule);
		// only for test --> execute rule 
		List<RuleExecution> ruleExecution = ruleService.executeRulesForAllVolunteers(derivationRule.getTenantId(), derivationRule.getContainer());
		
		// remove container rule again
		ruleService.deleteRule(derivationRule.getTenantId(), derivationRule.getContainer(), derivationRule.getName());
		return ruleExecution;
	}

	public void updateRule(String id, DerivationRuleDTO derivationRule) {
		derivationRuleRepository.save(derivationRuleMapper.toSource(derivationRule));
	}
	
	public List<PropertyDefinition<Object>> getGeneralProperties(String tenantId){
		List<PropertyDefinition<Object>> properties = new ArrayList<PropertyDefinition<Object>>();
		
		PropertyDefinition<Object> pd = (PropertyDefinition<Object>) propertyDefinitionRepository.getByNameAndTenantId("Alter", tenantId).get(0);
		properties.add(pd);
		
		return properties;
	}
	
}