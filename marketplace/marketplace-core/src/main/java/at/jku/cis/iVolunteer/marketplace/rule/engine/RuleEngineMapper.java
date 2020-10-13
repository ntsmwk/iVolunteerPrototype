package at.jku.cis.iVolunteer.marketplace.rule.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria.EQCriteria;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.ClassPropertyService;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.rule.Action;
import at.jku.cis.iVolunteer.model.rule.AttributeCondition;
import at.jku.cis.iVolunteer.model.rule.ClassAction;
import at.jku.cis.iVolunteer.model.rule.ClassCondition;
import at.jku.cis.iVolunteer.model.rule.Condition;
import at.jku.cis.iVolunteer.model.rule.DerivationRule;
import at.jku.cis.iVolunteer.model.rule.GeneralCondition;
import at.jku.cis.iVolunteer.model.rule.MultipleConditions;
import at.jku.cis.iVolunteer.model.rule.Action.ActionType;
import at.jku.cis.iVolunteer.model.rule.engine.RuleExecution;
import at.jku.cis.iVolunteer.model.rule.engine.RuleStatus;
import at.jku.cis.iVolunteer.model.rule.operator.AggregationOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.ComparisonOperatorType;
import at.jku.cis.iVolunteer.model.rule.operator.LogicalOperatorType;
import at.jku.cis.iVolunteer.model.user.User;

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
		return "package " + this.getClass().getPackage().getName() + ";\r\n";
		// return "package at.jku.cis.iVolunteer.marketplace.rule.engine;\r\n " +
		// "\r\n";
	}

	private String newGeneralImports() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(newImport(User.class.getName()));
		stringBuilder.append(newImport(Tenant.class.getName()));
		stringBuilder.append(newImport(UserService.class.getName()));
		stringBuilder.append(newImport(ClassInstanceService.class.getName()));
		stringBuilder.append(newImport(EQCriteria.class.getPackage().getName() + ".*"));
		stringBuilder.append(newImport(ClassInstance.class.getName()));
		stringBuilder.append(newImport(RuleExecution.class.getName()));
		stringBuilder.append(newImport(RuleStatus.class.getName()));
		stringBuilder.append(newImport(PropertyType.class.getName()));
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
		stringBuilder.append(newPattern("v", "User"));
		stringBuilder.append(newPattern("t", "Tenant"));
		stringBuilder.append(newPattern("re", "RuleExecution"));
		//
		stringBuilder.append(newPattern("l", "List"));
		stringBuilder.append(newPattern("vs", "UserService", true));

		for (int i = 0; i < derivationRule.getGeneralConditions().size(); i++) {
			GeneralCondition genCond = derivationRule.getGeneralConditions().get(i);
			stringBuilder.append(mapGeneralConditionToRuleConstraint(genCond));
			// AND - condition assumed --> insert "," after subcondition
			if (i < derivationRule.getGeneralConditions().size() - 1)
				stringBuilder.append(",\r\n");
		}
		stringBuilder.append(patternEnd()); // end volunteer service
		stringBuilder.append(newPattern("cis", "ClassInstanceService", true));

		for (int i = 0; i < derivationRule.getConditions().size(); i++) {
			Condition condition = derivationRule.getConditions().get(i);
			stringBuilder.append(mapConditionToRuleConstraint(condition));
			if (i < derivationRule.getConditions().size() - 1)
				stringBuilder.append(",\r\n");
		}
		stringBuilder.append(patternEnd());
		// check whether target already exists for volunteer

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
		// stringBuilder.append("System.out.println(\"Hello\");\r\n");
		stringBuilder.append("  re.setStatus(RuleStatus.FIRED);\r\n");
		for (Action action : derivationRule.getActions()) {
			if (action instanceof ClassAction)
				stringBuilder.append(mapClassActionToRuleAction((ClassAction) action, derivationRule.getId()));
			else {
				// XXX to do --> General Actions
			}
		}
		stringBuilder.append("\r\n");
		return stringBuilder.toString();
	}

	private String preventDuplicateInstance(List<ClassAction> classActions) {
		StringBuilder stringBuilder = new StringBuilder();
		for (ClassAction classAction : classActions) {
			if (classAction.getType() == ActionType.NEW) {
				ClassDefinition cd = classDefinitionRepository.findOne(classAction.getClassDefinitionId());
				if ((cd.getClassArchetype() == ClassArchetype.ACHIEVEMENT)
						|| (cd.getClassArchetype() == ClassArchetype.COMPETENCE)
						|| (cd.getClassArchetype() == ClassArchetype.FUNCTION)) {
					// no duplicate assets allowed
					stringBuilder.append("ClassInstanceService (");

					stringBuilder.append(")");
				}
			}
		}

		return stringBuilder.toString();
	}
	/*
	 * .newRule() .name(dRule.getName()) .lhs() .pattern().id("v",
	 * false).type("Volunteer").end() .pattern().id("t", false).type("Tenant").end()
	 * .pattern().id("vs", false).type("VolunteerService").
	 * constraint("getClassInstancesById(v, t.getId(), \"" +
	 * cRule.getClassDefinitionId()+"\").size() >= " + cRule.getValue()) .end()
	 * .end() .rhs("System.out.println(\"Volunteer is older than 18\");") .end();
	 */

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
			for (int i = 0; i < multiCond.getConditions().size(); i++) {
				Condition c = multiCond.getConditions().get(i);
				// evaluate subcondition
				s.append(mapConditionToRuleConstraint(c));
				// insert logical operator if necessary
				if (!multiCond.getLogicalOperator().equals(LogicalOperatorType.NOT)
						&& i < multiCond.getConditions().size() - 1)
					s.append(decodeLogicalOperator(multiCond.getLogicalOperator()));
			}
			s.append(" )");
		}
		return s.toString();

	}

	private String mapClassConditionToRuleConstraint(ClassCondition classCondition) {
		StringBuilder stringBuilder = new StringBuilder();
		//
		if (classCondition.getOperatorType().equals(AggregationOperatorType.SUM)) {
			stringBuilder.append(sumInstances(classCondition));
		} else {
			stringBuilder.append(countInstances(classCondition));
		}

		return stringBuilder.toString();
	}

	private String sumInstances(ClassCondition classCondition) {
		ClassProperty<Object> classProperty = classPropertyService.getClassPropertyFromAllClassProperties(
				classCondition.getClassDefinitionId(), classCondition.getClassPropertyId());
		StringBuilder stringBuilder = new StringBuilder();

		if (classProperty.getType().equals(PropertyType.FLOAT_NUMBER)) {
			stringBuilder.append(" (double) sum( ");
			stringBuilder.append(buildConstraintsForInstances(classCondition));
			stringBuilder.append(", new SumCriteria(\"" + classCondition.getClassPropertyId() + "\", \r\n "
					+ "		                   PropertyType.FLOAT_NUMBER )");
			stringBuilder.append("  ) >= " + classCondition.getValue() + "");
		} else if (classProperty.getType().equals(PropertyType.WHOLE_NUMBER)) {
			stringBuilder.append("(Integer) sum( ");
			stringBuilder.append(buildConstraintsForInstances(classCondition));
			stringBuilder.append(", new SumCriteria(\"" + classCondition.getClassPropertyId() + "\", \r\n "
					+ "		                   PropertyType.WHOLE_NUMBER )");
			stringBuilder.append(" ) >= " + classCondition.getValue());
		}
		// stringBuilder.append(")");
		return stringBuilder.toString();
	}

	private String countInstances(ClassCondition classCondition) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(buildConstraintsForInstances(classCondition) + decodeAggregationOperator(
				(AggregationOperatorType) classCondition.getOperatorType(), classCondition.getValue()));
		return stringBuilder.toString();
	}

	private String buildConstraintsForInstances(ClassCondition classCondition) {
		String constraints = "getClassInstances(v, \"" + classCondition.getClassDefinitionId() + "\", t.getId())";
		// get properties to filter for class instance
		for (AttributeCondition attrCondition : classCondition.getAttributeConditions()) {
			constraints = " filterInstancesByPropertyCriteria(" + constraints + ", "
					+ decodeAttributeCondition(attrCondition) + ")";
		}
		return constraints;
	}

	private String mapGeneralConditionToRuleConstraint(GeneralCondition generalCondition) {
		switch (generalCondition.getAttributeName()) {
		case "Alter":
			return "	v.getBirthday() != null && " + " currentAge( v ) "
					+ decodeComparisonOperator((ComparisonOperatorType) generalCondition.getOperatorType()) + " "
					+ generalCondition.getValue();
		default:
			return null;
		}
	}

	private String mapClassActionToRuleAction(ClassAction classAction, String derivationRuleId) {
		StringBuilder stringBuilder = new StringBuilder();
		switch (classAction.getType()) {
		case DELETE:
			stringBuilder.append(newDeleteAction(classAction));
			break;
		case NEW:
			stringBuilder.append(newInsertAction(classAction, derivationRuleId));
			break;
		case UPDATE:
			stringBuilder.append(newUpdateAction(classAction, derivationRuleId));
		default:
		}
		return stringBuilder.toString();
	}

	private String newUpdateAction(ClassAction classAction, String derivationRuleId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("  ClassInstance ci = cis.getClassInstance(v, \"" + classAction.getClassDefinitionId()
				+ "\", t.getId());\r\n");
		stringBuilder.append("  ci.setDerivationRuleId(\"" + derivationRuleId + "\");\r\n");
		for (AttributeCondition attribute : classAction.getAttributes()) {
			stringBuilder.append("  cis.setProperty(ci, \"" + attribute.getClassPropertyId() + "\", \""
					+ attribute.getValue().toString() + "\");\r\n");
		}
		stringBuilder.append("  l.add(ci);\r\n");
		// stringBuilder.append(" cis.saveClassInstance(ci);\r\n"); // XXX only for test
		return stringBuilder.toString();
	}

	private String newInsertAction(ClassAction classAction, String derivationRuleId) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("  ClassInstance ci = cis.newClassInstance(v, " + "\"" + classAction.getClassDefinitionId()
				+ "\", t.getId());\r\n");
		stringBuilder.append("  ci.setDerivationRuleId(\"" + derivationRuleId + "\");");
		for (AttributeCondition attribute : classAction.getAttributes()) {
			stringBuilder.append("  cis.setProperty(ci, \"" + attribute.getClassPropertyId() + "\", \""
					+ attribute.getValue().toString() + "\");\r\n");
		}
		stringBuilder.append("  l.add(ci);\r\n");
		// stringBuilder.append(" cis.saveClassInstance(ci);\r\n"); // XXX only for test
		return stringBuilder.toString();
	}

	private String newDeleteAction(ClassAction classAction) {
		StringBuilder stringBuilder = new StringBuilder();
		if (classAction.getAttributes().size() == 0) {
			// delete all instances of class
			stringBuilder.append("  cis.deleteClassInstances( v, " + "\"" + classAction.getClassDefinitionId()
					+ "\", t.getId());\r\n");
		} else {
			// filter properties
			String actions = "   cis.getClassInstances(v, \"" + classAction.getClassDefinitionId() + "\", t.getId())";
			for (AttributeCondition attrCondition : classAction.getAttributes()) {
				actions = " cis.filterInstancesByPropertyCriteria( \r\n" + actions + ", \r\n"
						+ decodeAttributeCondition(attrCondition) + ")";
			}
			actions = "cis.deleteClassInstances ( \r\n" + actions + " );";
			stringBuilder.append(actions);
		}
		return stringBuilder.toString();
	}

	private String decodeAttributeCondition(AttributeCondition attributeCondition) {
		String criteria = null;
		switch ((ComparisonOperatorType) attributeCondition.getOperatorType()) {
		case EQ:
			criteria = "new EQCriteria";
			break;
		case NE:
			criteria = "new NECriteria";
			break;
		case GT:
			criteria = "new GTCriteria";
			break;
		case GE:
			criteria = "new GECriteria";
			break;
		case LE:
			criteria = "new LECriteria";
			break;
		case LT:
			criteria = "new LTCriteria";
			break;
		default:
			break;
		}
		if (criteria != null)
			criteria += "(\"" + attributeCondition.getClassPropertyId() + "\", " + "\"" + attributeCondition.getValue()
					+ "\")";
		return criteria;
	}

	private String decodeAggregationOperator(AggregationOperatorType aggregationOperator, Object value) {
		switch (aggregationOperator) {
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
		case EQ:
			return " == ";
		case NE:
			return " != ";
		case GT:
			return " > ";
		case GE:
			return " >= ";
		case LT:
			return " < ";
		case LE:
			return " <= ";
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
