package at.jku.cis.iVolunteer.configurator.model.configurations.matching;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.IVolunteerObject;

@Document
public class MatchingConfiguration extends IVolunteerObject {
	private String name;

	private String leftSideId;
	private String leftSideName;
	private boolean leftIsUser;
	
	private List<String> leftAddedClassDefinitionPaths = new ArrayList<>();

	private String rightSideId;
	private String rightSideName;
	private boolean rightIsUser;
	
	private List<String> rightAddedClassDefinitionPaths = new ArrayList<>();
	
	private String hash;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getLeftSideId() {
		return leftSideId;
	}

	public void setLeftSideId(String leftSideId) {
		this.leftSideId = leftSideId;
	}

	public String getLeftSideName() {
		return leftSideName;
	}

	public void setLeftSideName(String leftSideName) {
		this.leftSideName = leftSideName;
	}

	public String getRightSideId() {
		return rightSideId;
	}

	public void setRightSideId(String rightSideId) {
		this.rightSideId = rightSideId;
	}

	public String getRightSideName() {
		return rightSideName;
	}

	public void setRightSideName(String rightSideName) {
		this.rightSideName = rightSideName;
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
