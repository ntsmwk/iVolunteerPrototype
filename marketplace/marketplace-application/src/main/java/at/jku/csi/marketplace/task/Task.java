package at.jku.csi.marketplace.task;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.task.type.TaskType;

@Document
public class Task {

	@Id
	private String id;
	private Date startDate;
	private Date endDate;
	private TaskStatus status;
	@DBRef
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
	public boolean equals(Object obj) {
		if (!(obj instanceof Task)) {
			return false;
		}
		return ((Task) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
