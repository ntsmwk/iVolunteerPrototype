package at.jku.cis.iVolunteer.marketplace.meta.core.class_.xnet;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;

@RestController
public class XTaskInstanceController {

	@Autowired XTaskInstanceService xTaskInstanceService;

	@GetMapping("/meta/core/task-instance/{taskId}")
	public TaskInstance getTaskInstance(@PathVariable("taskId") String id) {
		return xTaskInstanceService.getTaskInstance(id);
	}

}
