package at.jku.cis.marketplace.task;

import at.jku.cis.marketplace.task.interaction.TaskOperation;

public enum TaskStatus implements TaskOperation {
	CREATED, RUNNING, FINISHED, SUSPENDED, ABORTED;

}
