package at.jku.csi.marketplace.task.transaction;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskStatus;

@Document
public class TaskTransaction {

	@DBRef
	private Task task;
	private TransactionType transactionType;
	@DBRef
	private Volunteer volunteer;
	private Date timestamp;

	public TaskTransaction(Task task, TransactionType transactionType, Date timestamp) {
		this.task = task;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
	}

	public TaskTransaction(Task task, TransactionType transactionType, Volunteer volunteer,
			Date timestamp) {
		this(task, transactionType, timestamp);
		this.volunteer = volunteer;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
