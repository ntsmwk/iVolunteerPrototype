package at.jku.cis.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@EnableProcessApplication
@SpringBootApplication
public class WorkflowApplication {
	
	
	@Autowired
	private RuntimeService runtimeService;

public static void main(String[] args) {
		
	}
	
	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		runtimeService.startProcessInstanceByKey("simpleTask");
	}
	
	

}
