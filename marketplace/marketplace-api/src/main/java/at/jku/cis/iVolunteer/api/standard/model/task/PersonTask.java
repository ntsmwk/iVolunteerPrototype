package at.jku.cis.iVolunteer.api.standard.model.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.task.GeoInformation;

@Document
public class PersonTask {

	@Id private String id;
	private String taskId;
	private String taskName;
	private String taskType1;
	private String taskType2;
	private String taskType3;
	private String taskType4;
	private String taskDescription;
	private String taskDateFrom;
	private String taskDateTo;
	private String taskDuration;
	private String taskLocation;

	private String purpose;
	private String role;
	private String rank;
	private String phase;
	private String unit;
	private String level;

	private GeoInformation taskGeoInformation;
	private String iVolunteerUUID;
	private String iVolunteerSource;
	private String personID;

	public PersonTask() {
	}

	public PersonTask(String id, String taskId, String taskName, String taskType1, String taskType2, String taskType3,
			String taskType4, String taskDescription, String taskDateFrom, String taskDateTo, String taskDuration,
			String taskLocation, String purpose, String role, String rank, String phase, String unit, String level,
			GeoInformation taskGeoInformation, String iVolunteerUUID, String iVolunteerSource, String personID) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskType1 = taskType1;
		this.taskType2 = taskType2;
		this.taskType3 = taskType3;
		this.taskType4 = taskType4;
		this.taskDescription = taskDescription;
		this.taskDateFrom = taskDateFrom;
		this.taskDateTo = taskDateTo;
		this.taskDuration = taskDuration;
		this.taskLocation = taskLocation;
		this.purpose = purpose;
		this.role = role;
		this.rank = rank;
		this.phase = phase;
		this.unit = unit;
		this.level = level;
		this.taskGeoInformation = taskGeoInformation;
		this.iVolunteerUUID = iVolunteerUUID;
		this.iVolunteerSource = iVolunteerSource;
		this.personID = personID;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskType1() {
		return taskType1;
	}

	public void setTaskType1(String taskType1) {
		this.taskType1 = taskType1;
	}

	public String getTaskType2() {
		return taskType2;
	}

	public void setTaskType2(String taskType2) {
		this.taskType2 = taskType2;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskDateFrom() {
		return taskDateFrom;
	}

	public void setTaskDateFrom(String taskDateFrom) {
		this.taskDateFrom = taskDateFrom;
	}

	public String getTaskDateTo() {
		return taskDateTo;
	}

	public void setTaskDateTo(String taskDateTo) {
		this.taskDateTo = taskDateTo;
	}

	public String getTaskDuration() {
		return taskDuration;
	}

	public void setTaskDuration(String taskDuration) {
		this.taskDuration = taskDuration;
	}

	public String getTaskLocation() {
		return taskLocation;
	}

	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}

	public GeoInformation getTaskGeoInformation() {
		return taskGeoInformation;
	}

	public void setTaskGeoInformation(GeoInformation taskGeoInformation) {
		this.taskGeoInformation = taskGeoInformation;
	}

	public String getiVolunteerUUID() {
		return iVolunteerUUID;
	}

	public void setiVolunteerUUID(String iVolunteerUUID) {
		this.iVolunteerUUID = iVolunteerUUID;
	}

	public String getiVolunteerSource() {
		return iVolunteerSource;
	}

	public void setiVolunteerSource(String iVolunteerSource) {
		this.iVolunteerSource = iVolunteerSource;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getTaskType3() {
		return taskType3;
	}

	public void setTaskType3(String taskType3) {
		this.taskType3 = taskType3;
	}

	public String getTaskType4() {
		return taskType4;
	}

	public void setTaskType4(String taskType4) {
		this.taskType4 = taskType4;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

}
