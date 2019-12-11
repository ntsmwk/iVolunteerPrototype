package at.jku.cis.iVolunteer.model.meta.core.clazz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Document
public class ClassDefinition extends IVolunteerObject implements IHashObject {

	private String parentId;
	private String name;
	private List<ClassProperty<Object>> properties = new ArrayList<>();
	private ClassArchetype classArchetype;

	private String imagePath;
	
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
	

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public ClassArchetype getClassArchetype() {
		return classArchetype;
	}

	public void setClassArchetype(ClassArchetype classArchetype) {
		this.classArchetype = classArchetype;
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

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("marketplaceId", marketplaceId);
		json.addProperty("parent", parentId);
		json.addProperty("properties", this.properties.hashCode());
		json.addProperty("timestamp", timestamp.toString());
		return json.toString();
	}

}
