package at.jku.cis.marketplace.task;

import at.jku.cis.marketplace.task.interaction.TaskOperation;

public enum TaskStatus implements TaskOperation {
	CREATED, PUBLISHED, RUNNING, FINISHED, SUSPENDED, ABORTED;

}
