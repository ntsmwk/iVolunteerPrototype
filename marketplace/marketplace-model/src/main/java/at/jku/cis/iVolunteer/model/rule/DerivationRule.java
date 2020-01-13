package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class DerivationRule extends IVolunteerObject {

	private String name;
	private List<ClassSourceRuleEntry> classSourceRules = new ArrayList<>();
	private List<AttributeSourceRuleEntry> attributeSourceRules = new ArrayList<>();
	private String target;

	public DerivationRule() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<ClassSourceRuleEntry> getClassSourceRules() {
		return classSourceRules;
	}

	public void setClassSourceRules(List<ClassSourceRuleEntry> classSourceRules) {
		this.classSourceRules = classSourceRules;
	}

	public List<AttributeSourceRuleEntry> getAttributeSourceRules() {
		return attributeSourceRules;
	}

	public void setAttributeSourceRules(List<AttributeSourceRuleEntry> attributeSourceRules) {
		this.attributeSourceRules = attributeSourceRules;
	}

}