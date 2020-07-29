package at.jku.cis.iVolunteer.model.configurations.matching;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class MatchingConfiguration extends IVolunteerObject {
	private String name;

	private String leftClassConfigurationId;
	private String leftClassConfigurationName;

	private String rightClassConfigurationId;
	private String rightClassConfigurationName;

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
