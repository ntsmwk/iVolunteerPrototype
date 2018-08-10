package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.task.template.TaskTemplateMapper;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.TaskTemplateDTO;

@RestController
public class TaskTemplateController {

	@Autowired
	private TaskTemplateMapper taskTemplateMapper;
	@Autowired
	private TaskTemplateRepository taskTemplateRepository;

	@GetMapping("/taskTemplate")
	public List<TaskTemplateDTO> findAll() {
		return taskTemplateMapper.toDTOs(taskTemplateRepository.findAll());
	}

	@GetMapping("/taskTemplate/{id}")
	public TaskTemplateDTO findById(@PathVariable("id") String id) {
		return taskTemplateMapper.toDTO(taskTemplateRepository.findOne(id));
	}

	@PostMapping("/taskTemplate")
	public TaskTemplateDTO create(@RequestBody TaskTemplateDTO taskTemplateDto) {
		TaskTemplate taskTemplate = taskTemplateMapper.toEntity(taskTemplateDto);
		return taskTemplateMapper.toDTO(taskTemplateRepository.insert(taskTemplate));
	}

	@PutMapping("/taskTemplate/{id}")
	public TaskTemplateDTO update(@PathVariable("id") String id, @RequestBody TaskTemplateDTO taskTemplateDto) {
		if (!taskTemplateRepository.exists(id)) {
			throw new NotAcceptableException();
		}

		TaskTemplate taskTemplate = taskTemplateMapper.toEntity(taskTemplateDto);
		return taskTemplateMapper.toDTO(taskTemplateRepository.save(taskTemplate));
	}

	@DeleteMapping("/taskTemplate/{id}")
	public void delete(@PathVariable("id") String id) {
		taskTemplateRepository.delete(id);
	}

}
