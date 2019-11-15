package at.jku.cis.iVolunteer.model.meta.core.class_;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Document
public class ClassDefinition {

	@Id private String id;
	private String parentId;
	private String name;
	private List<ClassProperty<Object>> properties;

	private ClassArchetype archetype;

	boolean root;

	public ClassDefinition() {
	}

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

	public List<ClassProperty<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<ClassProperty<Object>> properties) {
		this.properties = properties;
	}

	public ClassArchetype getArchetype() {
		return archetype;
	}

	public void setArchetype(ClassArchetype archetype) {
		this.archetype = archetype;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassDefinition)) {
			return false;
		}
		return ((ClassDefinition) obj).id.equals(id);
	}

}
