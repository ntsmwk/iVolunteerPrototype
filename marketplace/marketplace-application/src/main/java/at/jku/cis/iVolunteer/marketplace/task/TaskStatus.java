package at.jku.cis.iVolunteer.marketplace.task;

import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskOperation;

public enum TaskStatus implements TaskOperation {
	CREATED, PUBLISHED, RUNNING, FINISHED, SUSPENDED, ABORTED;

}
