package at.jku.cis.iVolunteer.model.configurations.matching;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class MatchingConfiguration extends IVolunteerObject {
	private String name;

	private String leftClassConfigurationId;
	private String leftClassConfigurationName;
	private boolean leftIsUser;
	
	private List<String> leftAddedClassDefinitionPaths = new ArrayList<>();

	private String rightClassConfigurationId;
	private String rightClassConfigurationName;
	private boolean rightIsUser;
	
	private List<String> rightAddedClassDefinitionPaths = new ArrayList<>();
	
	private String hash;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeftClassConfigurationId() {
		return leftClassConfigurationId;
	}

	public void setLeftClassConfigurationId(String leftClassConfigurationId) {
		this.leftClassConfigurationId = leftClassConfigurationId;
	}

	public String getLeftClassConfigurationName() {
		return leftClassConfigurationName;
	}

	public void setLeftClassConfigurationName(String leftClassConfigurationName) {
		this.leftClassConfigurationName = leftClassConfigurationName;
	}

	public String getRightClassConfigurationId() {
		return rightClassConfigurationId;
	}

	public void setRightClassConfigurationId(String rightClassConfigurationId) {
		this.rightClassConfigurationId = rightClassConfigurationId;
	}

	public String getRightClassConfigurationName() {
		return rightClassConfigurationName;
	}

	public void setRightClassConfigurationName(String rightClassConfigurationName) {
		this.rightClassConfigurationName = rightClassConfigurationName;
	}
	
	public List<String> getLeftAddedClassDefinitionPaths() {
		return leftAddedClassDefinitionPaths;
	}

	public void setLeftAddedClassDefinitionPaths(List<String> leftAddedClassDefinitionPaths) {
		this.leftAddedClassDefinitionPaths = leftAddedClassDefinitionPaths;
	}

	public List<String> getRightAddedClassDefinitionPaths() {
		return rightAddedClassDefinitionPaths;
	}

	public void setRightAddedClassDefinitionPaths(List<String> rightAddedClassDefinitionPaths) {
		this.rightAddedClassDefinitionPaths = rightAddedClassDefinitionPaths;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	

	public boolean isLeftIsUser() {
		return leftIsUser;
	}

	public void setLeftIsUser(boolean leftIsUser) {
		this.leftIsUser = leftIsUser;
	}

	public boolean isRightIsUser() {
		return rightIsUser;
	}

	public void setRightIsUser(boolean rightIsUser) {
		this.rightIsUser = rightIsUser;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingConfiguration)) {
			return false;
		}
		return ((MatchingConfiguration) obj).id.equals(id);
	}

}
