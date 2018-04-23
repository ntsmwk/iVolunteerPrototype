package at.jku.cis.trustifier.model.task;

import at.jku.cis.trustifier.model.task.interaction.TaskOperation;

public enum TaskStatus implements TaskOperation {
	CREATED, RUNNING, FINISHED, SUSPENDED, ABORTED;

}
