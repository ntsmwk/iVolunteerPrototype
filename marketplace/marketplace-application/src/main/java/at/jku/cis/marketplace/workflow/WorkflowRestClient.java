package at.jku.cis.marketplace.workflow;

import static java.text.MessageFormat.format;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

public class WorkflowRestClient {

	private static final String VERIFIER_GET_WORKFLOW_TYPES_URI = "{0}/workflow/{1}";
	private static final String VERIFIER_CREATE_WORKFLOW_URI = "{0}/workflow/{1}";
	private static final String VERIFIER_GET_NEXT_WORKFLOW_TASKS_URI = "{0}/workflow/{1}/{2}/task";
	private static final String VERIFIER_COMPLETE_WORKFLOW_TASK_URI = "{0}/workflow/{1}/{2}/task";
	private static final String VERIFIER_DELETE_URI = "{0}/workflow/{1}/{2}";

	private static final String TYPES = "types";

	@Autowired
	private RestTemplate restTemplate;

	@Value("${marketplace.workflow.uri}")
	private URI workflowURI;

	public List<String> getWorkflowTypes() {
		String requestURI = buildWorkflowRequestURI(VERIFIER_GET_WORKFLOW_TYPES_URI, TYPES);
		return restTemplate.getForObject(requestURI, List.class);
	}

	public String createWorkFlow(String workflowTypeId) {
		String requestURI = buildWorkflowRequestURI(VERIFIER_CREATE_WORKFLOW_URI, workflowTypeId);
		return restTemplate.postForObject(requestURI, null, String.class);
	}

	public List<WorkflowStep> getNextTasksByInstanceId(String workflowTypeId, String workflowInstanceId) {
		String requestURI = buildWorkflowRequestURI(VERIFIER_GET_NEXT_WORKFLOW_TASKS_URI, workflowTypeId, workflowInstanceId);
		return restTemplate.getForObject(requestURI, List.class);
	}

	public void completeTask(String workflowTypeId, String workflowInstanceId, WorkflowStep workflowStep) {
		String requestURI = buildWorkflowRequestURI(VERIFIER_COMPLETE_WORKFLOW_TASK_URI, workflowTypeId, workflowInstanceId);
		restTemplate.postForObject(requestURI, workflowStep, Void.class);
	}

	public void cancelWorkflow(String workflowTypeId, String workflowInstanceId) {
		String requestURI = buildWorkflowRequestURI(VERIFIER_DELETE_URI, workflowTypeId, workflowInstanceId);
		restTemplate.delete(requestURI);
	}

	private String buildWorkflowRequestURI(String verifierUri, String... params) {
		return format(verifierUri, workflowURI, params);
	}

}
