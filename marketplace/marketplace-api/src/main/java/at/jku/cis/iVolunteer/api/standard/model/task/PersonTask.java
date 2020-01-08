package at.jku.cis.iVolunteer.api.standard.model.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PersonTask {

	@Id private String id;
	private int taskID;
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

	private String Zweck;
	private String Rolle;
	private String Rang;
	private String Phase;
	private String Arbeitsteilung;
	private String Ebene;

	private GeoInformation taskGeoInformation;
	private String iVolunteerUUID;
	private String iVolunteerSource;
	private String personID;

	public PersonTask() {
	}

	public PersonTask(String id, int taskID, String taskName, String taskType1, String taskType2, String taskType3,
			String taskType4, String taskDescription, String taskDateFrom, String taskDateTo, String taskDuration,
			String taskLocation, String zweck, String rolle, String rang, String phase, String arbeitsteilung,
			String ebene, GeoInformation taskGeoInformation, String iVolunteerUUID, String iVolunteerSource,
			String personID) {
		super();
		this.id = id;
		this.taskID = taskID;
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
		this.Zweck = zweck;
		this.Rolle = rolle;
		this.Rang = rang;
		this.Phase = phase;
		this.Arbeitsteilung = arbeitsteilung;
		this.Ebene = ebene;
		this.taskGeoInformation = taskGeoInformation;
		this.iVolunteerUUID = iVolunteerUUID;
		this.iVolunteerSource = iVolunteerSource;
		this.personID = personID;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getZweck() {
		return Zweck;
	}

	public void setZweck(String zweck) {
		Zweck = zweck;
	}

	public String getRolle() {
		return Rolle;
	}

	public void setRolle(String rolle) {
		Rolle = rolle;
	}

	public String getRang() {
		return Rang;
	}

	public void setRang(String rang) {
		Rang = rang;
	}

	public String getPhase() {
		return Phase;
	}

	public void setPhase(String phase) {
		Phase = phase;
	}

	public String getArbeitsteilung() {
		return Arbeitsteilung;
	}

	public void setArbeitsteilung(String arbeitsteilung) {
		Arbeitsteilung = arbeitsteilung;
	}

	public String getEbene() {
		return Ebene;
	}

	public void setEbene(String ebene) {
		Ebene = ebene;
	}

}
