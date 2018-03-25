package at.jku.csi.marketplace.task;

import at.jku.csi.marketplace.task.interaction.TaskOperation;

public enum TaskStatus implements TaskOperation {
	CREATED, RUNNING, FINISHED, SUSPENDED, ABORTED;

}