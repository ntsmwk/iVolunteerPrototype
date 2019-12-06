package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

public class DerivationRuleDTO {

	private String id;
	private String name;
	private String marketplaceId;
	private List<SourceRuleEntryDTO> sources = new ArrayList<>();
	private List<ClassDefinition> targets = new ArrayList<>();

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

	public List<SourceRuleEntryDTO> getSources() {
		return sources;
	}

	public void setSources(List<SourceRuleEntryDTO> sources) {
		this.sources = sources;
	}

	public List<ClassDefinition> getTargets() {
		return targets;
	}

	public void setTargets(List<ClassDefinition> targets) {
		this.targets = targets;
	}

}
