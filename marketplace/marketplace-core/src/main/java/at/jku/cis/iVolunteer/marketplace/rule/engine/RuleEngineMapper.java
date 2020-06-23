package at.jku.cis.iVolunteer.marketplace.rule.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.rule.Action;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.Condition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;

@Component
public class RuleEngineMapper {
	
	@Autowired ClassDefinitionRepository classDefinitionRepository;
	@Autowired ClassPropertyService classPropertyService;
	
	public String generateDroolsRuleFrom(DerivationRule derivationRule) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(newPackage());
		stringBuilder.append(newGeneralImports());
		stringBuilder.append(newAttribute("dialect", "\"mvel\""));
		stringBuilder.append("\r\n");
		stringBuilder.append(newRule(derivationRule));
		return stringBuilder.toString();
	}
	
	
	private String newPackage() {
		return "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n " + 
	           "\r\n";
	}
	
	private String newGeneralImports() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.model.user.Volunteer"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.model.core.tenant.Tenant"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.user.VolunteerService"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.*"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.model.rule.engine.RuleExecution"));
		stringBuilder.append(newImport("java.util.List"));
		return stringBuilder.toString();
	}
	
	private String newImport(String className) {
		return "import " + className + ";\r\n";
	}
	
	private String newAttribute(String attribute, String value) {
		return attribute + " " + value + "\r\n";
	}
	
	public String newRule(DerivationRule derivationRule) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("rule \"" + derivationRule.getName() + "\"\r\n");
		stringBuilder.append(lhs(derivationRule));
		stringBuilder.append(rhs(derivationRule));
		stringBuilder.append("end \r\n");
		return stringBuilder.toString();
	}
	
	private String lhs(DerivationRule derivationRule) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("when\r\n");
		stringBuilder.append(newPattern("v", "Volunteer"));
		stringBuilder.append(newPattern("t", "Tenant"));
		stringBuilder.append(newPattern("re", "RuleExecution"));
		// 
		stringBuilder.append(newPattern("vs", "VolunteerService", true));
		
		for (int i=0; i < derivationRule.getGeneralConditions().size(); i++) {
			GeneralCondition genCond = derivationRule.getGeneralConditions().get(i);
			stringBuilder.append(mapGeneralConditionToRuleConstraint(genCond));
			// AND - condition assumed --> insert "," after subcondition
			if (i < derivationRule.getGeneralConditions().size()-1) 
				stringBuilder.append(",\r\n");
		}
		stringBuilder.append(patternEnd()); // end volunteer service
		stringBuilder.append(newPattern("cis", "ClassInstanceService", true));
		
		System.out.println(" conditions: " + derivationRule.getConditions().size());

		for (int i=0; i < derivationRule.getConditions().size(); i++) {
			Condition condition = derivationRule.getConditions().get(i);
			stringBuilder.append(mapConditionToRuleConstraint(condition));
			if (i < derivationRule.getConditions().size()-1)
				stringBuilder.append(",\r\n");
		} 
		stringBuilder.append(patternEnd());
		return stringBuilder.toString();
	}
	
	
	private String newPattern(String id, String type) {
		return " " + id + ": " + type + "( ) \r\n";
	}
	
	private String newPattern(String id, String type, boolean constraints) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" " + id + ": " + type + " ( ");
		return stringBuilder.toString();
	}
	private String patternEnd() {
		return " )\r\n";
	}
	
	private String rhs(DerivationRule derivationRule) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("then\r\n ");
		stringBuilder.append("  re.setFired();\r\n");
		for (Action action: derivationRule.getActions()) {
			if (action instanceof ClassAction)
				stringBuilder.append(mapClassActionToRuleAction((ClassAction)action));
			else {
				// XXX to do
			}
		}
		stringBuilder.append("\r\n");
		return stringBuilder.toString();
	}
	/*
	.newRule() 
    .name(dRule.getName()) 
    .lhs()
    .pattern().id("v", false).type("Volunteer").end()
    .pattern().id("t", false).type("Tenant").end()
    .pattern().id("vs", false).type("VolunteerService").
                constraint("getClassInstancesById(v, t.getId(), \"" + cRule.getClassDefinitionId()+"\").size() >= " + cRule.getValue())
                .end()
    .end()
    .rhs("System.out.println(\"Volunteer is older than 18\");")
    .end();*/
	
	private String mapConditionToRuleConstraint(Condition condition) {
		StringBuilder s = new StringBuilder();
		if (condition instanceof ClassCondition)
			// we have reached a leaf --> class condition
			return mapClassConditionToRuleConstraint((ClassCondition) condition);
		else if (condition instanceof MultipleConditions) {
			// we are at a node --> dissect multiple conditions
			MultipleConditions multiCond = (MultipleConditions) condition;
			
			if (multiCond.getLogicalOperator().equals(LogicalOperatorType.NOT))
				s.append(decodeLogicalOperator(multiCond.getLogicalOperator()));
		    
			s.append(" ( ");
			for(int i=0; i < multiCond.getConditions().size(); i++) {
				Condition c = multiCond.getConditions().get(i);
				// evaluate subcondition
				s.append(mapConditionToRuleConstraint(c));
				// insert logical operator if necessary 
				if (!multiCond.getLogicalOperator().equals(LogicalOperatorType.NOT) &&
					i < multiCond.getConditions().size()-1)
					s.append(decodeLogicalOperator(multiCond.getLogicalOperator()));
			}
			s.append(" )");
		}
		return s.toString();
			
	}
	
	private String mapClassConditionToRuleConstraint(ClassCondition classCondition) {
		StringBuilder stringBuilder = new StringBuilder();
		String constraints = "getClassInstances(v, \"" + classCondition.getClassDefinitionId() + "\", t.getId())";
		// get properties to filter for class instance
		for (AttributeCondition attrCondition: classCondition.getAttributeConditions()) {
			constraints = " filterInstancesByPropertyCriteria("+ constraints + ", " + 
						decodeAttributeCondition(attrCondition) + ")";
		}
		stringBuilder.append(
			constraints + 
		    decodeAggregationOperator((AggregationOperatorType)classCondition.getOperatorType(), 
		    		                  classCondition.getValue())); 
		
		return stringBuilder.toString();
	}
	

	private String mapGeneralConditionToRuleConstraint(GeneralCondition generalCondition) {
		switch (generalCondition.getAttributeName()) {
			case "Alter":
				return "	currentAge( v ) " + decodeComparisonOperator((ComparisonOperatorType)generalCondition.getOperatorType()) + 
						       " " + generalCondition.getValue(); 
			default: return null;
		}
	}
	
	private String mapClassActionToRuleAction(ClassAction classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		switch (classAction.getType()) {
		case DELETE:
			stringBuilder.append(newDeleteAction(classAction));
			break;
		case NEW: 
			stringBuilder.append(newInsertAction(classAction));
			break;
		case UPDATE:
			stringBuilder.append(newUpdateAction(classAction));
			default:
		}
		return stringBuilder.toString();
	}
	
	private String newUpdateAction(ClassAction classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("  ClassInstance ci = cis.getClassInstance(v, \"" + 
	             classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		for (AttributeCondition attribute: classAction.getAttributes()) {
			stringBuilder.append("  cis.setProperty(ci, \"" + 
		                              attribute.getClassPropertyId() + "\", \"" + 
					                  attribute.getValue().toString() + "\");\r\n");
		}
		return stringBuilder.toString();
	}
	
	private String newInsertAction(ClassAction classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println(" new insert action for class: " + classAction.getClassDefinitionId());
		stringBuilder.append("  ClassInstance ci = cis.newClassInstance(v, " + 
		         "\"" + classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		for (AttributeCondition attribute: classAction.getAttributes()) {
			System.out.println(" attribute-Id: " + attribute.getClassPropertyId() + " attribute: " + attribute.getValue());
			stringBuilder.append("  cis.setProperty(ci, \"" + 
		                              attribute.getClassPropertyId() + "\", \"" + 
					                  attribute.getValue().toString() + "\");\r\n");
		}
		return stringBuilder.toString();
	}
	
	private String newDeleteAction(ClassAction classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		if (classAction.getAttributes().size() == 0) {
			// delete all instances of class
			stringBuilder.append("  cis.deleteClassInstances( v, " + 
					 "\"" + classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		} else {
			// filter properties
			String actions = "   cis.getClassInstances(v, \"" + classAction.getClassDefinitionId() + "\", t.getId())";
			for (AttributeCondition attrCondition: classAction.getAttributes()) {
				actions = " cis.filterInstancesByPropertyCriteria( \r\n"+ actions + ", \r\n" + 
							decodeAttributeCondition(attrCondition) + ")";
			}
			actions = "cis.deleteClassInstances ( \r\n" + actions + " );";
			stringBuilder.append(actions);
		}
		return stringBuilder.toString();
	}
	
	private String decodeAttributeCondition(AttributeCondition attributeCondition) {
		String criteria = null;
		switch((ComparisonOperatorType)attributeCondition.getOperatorType()) {		
		case EQ: criteria = "new EQCriteria"; break;
		case NE: criteria = "new NECriteria"; break;	
		case GT: criteria = "new GTCriteria"; break;
		case GE: criteria = "new GECriteria"; break;
		case LE: criteria = "new LECriteria"; break;
		case LT: criteria = "new LTCriteria"; break;
		default:
			break;
		}
		if (criteria != null)
			criteria += "(\"" + attributeCondition.getClassPropertyId() + "\", " + 
						"\"" + attributeCondition.getValue() + "\")";
		return criteria;
	}
	
	private String decodeAggregationOperator(AggregationOperatorType aggregationOperator, Object value) {
		switch(aggregationOperator) {
		case COUNT:
			return ".size() == " + value; 
		case EXISTS:
			return ".size() > 0";
		case NOT_EXISTS:
			return ".size() == 0";
		case MIN:
			return ".size() >= " + value;
		case MAX: 
			return ".size() <= " + value;
		}
		return null;
	}
	
	private String decodeComparisonOperator(ComparisonOperatorType comparisonOperator) {
		switch (comparisonOperator) {
		case EQ: return " == ";
		case NE: return " != ";
		case GT: return " > "; 
		case GE: return " >= ";
		case LT: return " < ";
		case LE: return " <= ";
		default:
			break;
		}
		return null;
	}
	
	private String decodeLogicalOperator(LogicalOperatorType logicalOperator) {
		switch (logicalOperator) {
		case AND:
			return " && "; 
		case OR: 
			return " || ";
		case NOT:
			return " ! ";
		}
		return null;
	}
	
}
