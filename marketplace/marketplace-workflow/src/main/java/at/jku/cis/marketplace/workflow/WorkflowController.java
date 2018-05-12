package at.jku.cis.marketplace.workflow;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

	@PostMapping
	public void startWorkflow(@RequestParam("taskId") String taskId) {

	}
}
