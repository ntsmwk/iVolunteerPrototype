package at.jku.cis.trustifier.model.task;

import java.util.Date;

import at.jku.cis.trustifier.hash.IHashObject;
import at.jku.cis.trustifier.model.task.type.TaskType;

public class Task implements IHashObject {

	private String id;
	private Date startDate;
	private Date endDate;
	private TaskStatus status;
	private TaskType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	@Override
	public String toHashObject() {
		//TODO
		return "test";
	}
}
