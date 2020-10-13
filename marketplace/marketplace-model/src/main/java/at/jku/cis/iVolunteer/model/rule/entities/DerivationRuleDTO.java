package at.jku.cis.iVolunteer.model.rule.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DerivationRuleDTO {

	private String id;
	private String tenantId;
	private String name;
	private String container;
	private String marketplaceId;
	private List<GeneralConditionDTO> generalConditions = new ArrayList<GeneralConditionDTO>();
	private List<ClassConditionDTO> conditions = new ArrayList<ClassConditionDTO>();
	private List<ClassActionDTO> classActions = new ArrayList<ClassActionDTO>();
	private Date timestamp;
	private boolean active;
	private int fireNumOfTimes;

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

	public List<GeneralConditionDTO> getGeneralConditions() {
		return generalConditions;
	}

	public void setGeneralConditions(List<GeneralConditionDTO> list) {
		this.generalConditions = list;
	}

	public void addGeneralCondition(GeneralConditionDTO generalCondition) {
		generalConditions.add(generalCondition);
	}

	public List<ClassConditionDTO> getConditions() {
		return conditions;
	}

	public void setConditions(List<ClassConditionDTO> conditions) {
		this.conditions = conditions;
	}

	public void addCondition(ClassConditionDTO condition) {
		conditions.add(condition);
	}

	public List<ClassActionDTO> getClassActions() {
		return classActions;
	}

	public void setClassActions(List<ClassActionDTO> actions) {
		this.classActions = actions;
	}

	public void addClassAction(ClassActionDTO action) {
		classActions.add(action);
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return active;
	}
	
	public void setFireNumOfTimes(int fireNumOfTimes) {
		this.fireNumOfTimes = fireNumOfTimes;
	}

	public int getFireNumOfTimes() {
		return fireNumOfTimes;
	}

}
