package at.jku.cis.iVolunteer.model.meta.core.class_.dtos;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

public class ClassDefinitionDTO {

	private String id;
	private String parentId;
	private String name;
	private List<ClassPropertyDTO<Object>> properties;
	private List<MatchingRule> matchingRules;
	private ClassArchetype archetype;

	private boolean root;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassPropertyDTO<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<ClassPropertyDTO<Object>> properties) {
		this.properties = properties;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public List<MatchingRule> getMatchingRules() {
		return matchingRules;
	}

	public void setMatchingRules(List<MatchingRule> matchingRules) {
		this.matchingRules = matchingRules;
	}

	public ClassArchetype getArchetype() {
		return archetype;
	}

	public void setArchetype(ClassArchetype archetype) {
		this.archetype = archetype;
	}

}
