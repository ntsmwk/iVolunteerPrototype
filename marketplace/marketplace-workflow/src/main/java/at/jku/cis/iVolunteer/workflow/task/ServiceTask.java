package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.JavaDelegate;

public interface ServiceTask extends JavaDelegate {

	public static final String TASK_ID = "taskId";
	public static final String EMPOYEE_ID = "employeeId";

	public static final String VOLUNTEER_ID = "volunteerId";
	public static final String VOLUNTEER_IDS = "volunteerIds";

	public static final String TOKEN = "token";

}