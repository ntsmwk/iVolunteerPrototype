package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.rule.archive.SourceRuleEntryDTO;
import at.jku.cis.iVolunteer.model.rule.archive.TargetRuleEntryDTO;

public class DerivationRuleDTO {

	private String id;
	private String tenantId;
	private String name;
	private String container;
	private String marketplaceId;
	private List<SourceRuleEntryDTO> lhsConditions = new ArrayList<>();
	private List<TargetRuleEntryDTO> rhsActions = new ArrayList<>();
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

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public List<SourceRuleEntryDTO> getLhsConditions() {
		return lhsConditions;
	}

	public void setLhsConditions(List<SourceRuleEntryDTO> lhsConditions) {
		this.lhsConditions = lhsConditions;
	}
	
	public List<TargetRuleEntryDTO> getRhsActions() {
		return rhsActions;
	}

	public void setRhsActions(List<TargetRuleEntryDTO> rhsActions) {
		this.rhsActions = rhsActions;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
