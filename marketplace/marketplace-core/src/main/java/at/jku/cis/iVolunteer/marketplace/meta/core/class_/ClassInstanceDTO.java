package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.Date;

public class ClassInstanceDTO {
	private String name;
	private Date blockchainDate;
	private String purpose;
	private String dateFrom;
	private String dateTo;
	private String location;
	private String description;
	private String duration;
	private String rank;
	private String taskType1;
	private String taskType2;
	private String taskType3;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBlockchainDate() {
		return blockchainDate;
	}

	public void setBlockchainDate(Date blockchainDate) {
		this.blockchainDate = blockchainDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public String getTaskType3() {
		return taskType3;
	}

	public void setTaskType3(String taskType3) {
		this.taskType3 = taskType3;
	}

	@Override
	public String toString() {
		return "ClassInstanceDTO [name=" + name + ", blockchainDate=" + blockchainDate + ", purpose=" + purpose
				+ ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", location=" + location + ", description="
				+ description + ", duration=" + duration + ", rank=" + rank + ", taskType1=" + taskType1
				+ ", taskType2=" + taskType2 + ", taskType3=" + taskType3 + "]" + "\n";
	}

}
