package at.jku.cis.iVolunteer.model.meta.core.clazz;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Document
public class ClassInstance extends IVolunteerObject implements IHashObject {

	private String classDefinitionId;
	private String name;
	private List<PropertyInstance<Object>> properties;
	
	private String userId;
	private String issuerId;
	
	//Temp flags for dashboard presentation
	private boolean published; // flag if published
	private boolean inUserRepository; //flag if in inbox or in repository
	private boolean inIssuerRepository; //flag für Properties Imported via API
	
	private ClassArchetype classArchetype;
	

	public ClassInstance() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyInstance<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyInstance<Object>> properties) {
		this.properties = properties;
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassInstance))
			return false;
		return ((ClassInstance) obj).id.equals(id);
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("marketplaceId", marketplaceId);
//TODO @MWE		json.addProperty("classDefinition", classDefinition.toHashObject());
		json.addProperty("properties", this.properties.hashCode());
		json.addProperty("timestamp", timestamp.toString());
		return json.toString();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public ClassArchetype getClassArchetype() {
		return classArchetype;
	}

	public void setClassArchetype(ClassArchetype classArchetype) {
		this.classArchetype = classArchetype;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public boolean isInUserRepository() {
		return inUserRepository;
	}

	public void setInUserRepository(boolean inUserRepository) {
		this.inUserRepository = inUserRepository;
	}

	public boolean isInIssuerRepository() {
		return inIssuerRepository;
	}

	public void setInIssuerRepository(boolean inIssuerRepository) {
		this.inIssuerRepository = inIssuerRepository;
	}

	
	
	

}
