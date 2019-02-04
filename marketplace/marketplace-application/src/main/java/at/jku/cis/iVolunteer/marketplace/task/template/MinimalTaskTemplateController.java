//TODO: Just for testing - DELETE


package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.task.template.MinimalTaskTemplateMapper;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.task.template.MinimalTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.MinimalTaskTemplateDTO;

@RestController
public class MinimalTaskTemplateController {
	
	@Autowired private MinimalTaskTemplateMapper mapper;
	@Autowired private MinimalTaskTemplateRepository repository;
	

	
	
	//Retrieve from Server
	@GetMapping("/minimalTaskTemplate")
	public List<MinimalTaskTemplateDTO> findAll() {
		//return mapper.toDTOs(repository.findAll());
		
		//TESTING
		List<MinimalTaskTemplate> templates = new LinkedList<>();
		templates.add(new MinimalTaskTemplate());
		templates.add(new MinimalTaskTemplate("1112332", "Orsch", "Da Orsch vom Schorsch"));
		templates.add(new MinimalTaskTemplate("eflienl", "Muzi", "eflainelifnaelfi nelin fliaen lin afölifen aölif "));
		return mapper.toDTOs(templates);
		
	}
	
	
	@GetMapping("/minimalTaskTemplate/{id}")
	public MinimalTaskTemplateDTO findOne(@PathVariable("id") String id) {
		return mapper.toDTO(repository.findOne(id));
	}
	
	//Add entry to server
	@PostMapping("minimalTaskTemplate")
	public MinimalTaskTemplateDTO addOne (@RequestBody MinimalTaskTemplateDTO m) {
		//MinimalTaskTemplate mtt = mapper.toEntity(m);
		
		return mapper.toDTO(repository.insert(mapper.toEntity(m)));
	}
	
	//Update entry on server
	@PutMapping("minimalTaskTemplate/{id}")
	public MinimalTaskTemplateDTO updateOne(@PathVariable("id") String id, @RequestBody MinimalTaskTemplateDTO m) {
		if (repository.exists(id)) {
			throw new NotAcceptableException();
		} else {
			return mapper.toDTO(repository.save(mapper.toEntity(m)));
		}
	}
	
	//Delete entry from server
	@DeleteMapping("minimalTaskTemplate/{id}")
	public void deleteOne(@PathVariable("id") String id) {
		repository.delete(id);
	}
	
	@GetMapping("/minimalTaskTemplate/crazy")
	public MinimalTaskTemplateDTO doShit() {
		return mapper.toDTO(new MinimalTaskTemplate()); 
	}
	
	
	

}
