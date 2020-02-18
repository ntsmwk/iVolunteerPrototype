package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class DerivationRuleDTO {

	private String id;
	private String tenantId;
	private String name;
	private String marketplaceId;
	private List<ClassSourceRuleEntryDTO> classSourceRules = new ArrayList<>();
	private List<AttributeSourceRuleEntryDTO> attributeSourceRules = new ArrayList<>();
	private ClassDefinition target;
	private Date timestamp;

	public DerivationRuleDTO() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ClassDefinition getTarget() {
		return target;
	}

	public void setTarget(ClassDefinition target) {
		this.target = target;
	}

	public List<ClassSourceRuleEntryDTO> getClassSourceRules() {
		return classSourceRules;
	}

	public void setClassSourceRules(List<ClassSourceRuleEntryDTO> classSourceRules) {
		this.classSourceRules = classSourceRules;
	}

	public List<AttributeSourceRuleEntryDTO> getAttributeSourceRules() {
		return attributeSourceRules;
	}

	public void setAttributeSourceRules(List<AttributeSourceRuleEntryDTO> attributeSourceRules) {
		this.attributeSourceRules = attributeSourceRules;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
