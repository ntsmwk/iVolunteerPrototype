package at.jku.cis.iVolunteer.marketplace.rule.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.Criteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.EQCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.GTCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.SingleCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.AttributeSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassActionRuleEntry;
import at.jku.cis.iVolunteer.model.rule.ClassAggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.ClassSourceRuleEntry;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralAttributeEntry;
import at.jku.cis.iVolunteer.model.rule.MappingOperatorType;

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
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.EQCriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.NECriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.GTCriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.GECriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.LTCriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.marketplace.meta.core.class_.LECriteria"));
		stringBuilder.append(newImport("at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance"));
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
		// 
		stringBuilder.append(newPattern("vs", "VolunteerService", true));
		for (GeneralAttributeEntry genAttr: derivationRule.getLhsGeneralConditions()) {
			if (derivationRule.getLhsGeneralConditions().indexOf(genAttr) > 0)
				stringBuilder.append(",\r\n");
			stringBuilder.append(mapGeneralAttributeToRuleConstraint(genAttr));
		}
		stringBuilder.append(patternEnd()); // end volunteer service
		stringBuilder.append(newPattern("cis", "ClassInstanceService", true));
		for (ClassSourceRuleEntry classRuleEntry: derivationRule.getLhsClassConditions()) {
			stringBuilder.append(mapClassSourceRuleEntryToRuleConstraint(classRuleEntry));
			if (derivationRule.getLhsClassConditions().indexOf(classRuleEntry) < derivationRule.getLhsClassConditions().size()-1)
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
		stringBuilder.append(" " + id + ": " + type + "( ");
		return stringBuilder.toString();
	}
	private String patternEnd() {
		return " )\r\n";
	}
	
	private String rhs(DerivationRule derivationRule) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("then\r\n ");
		stringBuilder.append("  System.out.println(\" Bedingung wird erfüllt, yay!!!! \");\r\n");
		for (ClassActionRuleEntry classAction: derivationRule.getRhsRuleActions()) {
			stringBuilder.append(mapClassActionRuleEntryToRuleAction(classAction));
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
	
	private String mapClassSourceRuleEntryToRuleConstraint(ClassSourceRuleEntry classSourceRuleEntry) {
		StringBuilder stringBuilder = new StringBuilder();
		String constraints = "getClassInstances(v, \"" + classSourceRuleEntry.getClassDefinitionId() + "\", t.getId())";
		// get properties to filter for class instance
		for (AttributeSourceRuleEntry attrRule: classSourceRuleEntry.getAttributeSourceRules()) {
			constraints = " filterInstancesByPropertyCriteria("+ constraints + ", " + 
						decodeAttributeSourceRuleEntry(attrRule) + ")";
		}
		stringBuilder.append(
			constraints + 
		    decodeAggregationOperator(classSourceRuleEntry.getAggregationOperatorType(), 
		    		                  classSourceRuleEntry.getValue())); 
		
		return stringBuilder.toString();
	}
	

	private String mapGeneralAttributeToRuleConstraint(GeneralAttributeEntry generalAttribute) {
		switch (generalAttribute.getAttribute()) {
			case AGE:
				return "	currentAge( v ) " + decodeMappingOperator(generalAttribute.getMappingOperatorType()) + " " + generalAttribute.getValue(); 
			default: return null;
		}
	}
	
	private String mapClassActionRuleEntryToRuleAction(ClassActionRuleEntry classActionRuleEntry) {
		StringBuilder stringBuilder = new StringBuilder();
		switch (classActionRuleEntry.getClassRuleActionType()) {
		case DELETE:
			stringBuilder.append(newDeleteAction(classActionRuleEntry));
			break;
		case NEW: 
			stringBuilder.append(newInsertAction(classActionRuleEntry));
			break;
		case UPDATE:
			stringBuilder.append(newUpdateAction(classActionRuleEntry));
			default:
		}
		return stringBuilder.toString();
	}
	
	private String newUpdateAction(ClassActionRuleEntry classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("  ClassInstance ci = cis.getClassInstance(v, \"" + 
	             classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		for (AttributeSourceRuleEntry attribute: classAction.getAttributeSourceRules()) {
			stringBuilder.append("  cis.setProperty(ci, \"" + 
		                              attribute.getClassPropertyId() + "\", \"" + 
					                  attribute.getValue().toString() + "\");\r\n");
		}
		return stringBuilder.toString();
	}
	
	private String newInsertAction(ClassActionRuleEntry classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("  ClassInstance ci = cis.newClassInstance(v, " + 
		         "\"" + classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		for (AttributeSourceRuleEntry attribute: classAction.getAttributeSourceRules()) {
			stringBuilder.append("  cis.setProperty(ci, \"" + 
		                              attribute.getClassPropertyId() + "\", \"" + 
					                  attribute.getValue().toString() + "\");\r\n");
		}
		return stringBuilder.toString();
	}
	
	private String newDeleteAction(ClassActionRuleEntry classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		if (classAction.getAttributeSourceRules().size() == 0) {
			// delete all instances of class
			stringBuilder.append("  cis.deleteClassInstances( v, " + 
					 "\"" + classAction.getClassDefinitionId() + "\", t.getId());\r\n");
		} else {
			// filter properties
			String actions = "   cis.getClassInstances(v, \"" + classAction.getClassDefinitionId() + "\", t.getId())";
			for (AttributeSourceRuleEntry attrRule: classAction.getAttributeSourceRules()) {
				actions = " cis.filterInstancesByPropertyCriteria( \r\n"+ actions + ", \r\n" + 
							decodeAttributeSourceRuleEntry(attrRule) + ")";
			}
			actions = "cis.deleteClassInstances ( \r\n" + actions + " );";
			stringBuilder.append(actions);
		}
		return stringBuilder.toString();
	}
	
	private String decodeAttributeSourceRuleEntry(AttributeSourceRuleEntry attributeSourceRuleEntry) {
		String criteria = null;
		switch(attributeSourceRuleEntry.getMappingOperatorType()) {		
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
			criteria += "(\"" + attributeSourceRuleEntry.getClassPropertyId() + "\", " + 
						"\"" + attributeSourceRuleEntry.getValue() + "\")";
		return criteria;
	}
	
	private String decodeAggregationOperator(ClassAggregationOperatorType classAggregationOperatorType, Object value) {
		switch(classAggregationOperatorType) {
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
	
	private String decodeMappingOperator(MappingOperatorType mappingOperatorType) {
		switch (mappingOperatorType) {
		case GT: return " > "; 
		case GE: return " >= ";
		case LT: return " < ";
		case LE: return " <= ";
		default:
			break;
		}
		return null;
	}
}
