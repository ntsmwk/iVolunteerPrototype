package at.jku.cis.iVolunteer.model.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class DerivationRule extends IVolunteerObject {

	private String name;
	private List<SourceRuleEntry> sources = new ArrayList<>();
	private List<String> targets = new ArrayList<>();

	public DerivationRule() {
	}

	public List<SourceRuleEntry> getSources() {
		return sources;
	}

	public void setSources(List<SourceRuleEntry> sources) {
		this.sources = sources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTargets() {
		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}
}