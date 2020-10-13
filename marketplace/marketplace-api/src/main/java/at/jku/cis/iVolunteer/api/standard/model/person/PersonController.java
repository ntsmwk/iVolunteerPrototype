package at.jku.cis.iVolunteer.api.standard.model.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/Person")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@GetMapping()
	public List<Person> findAll(){
		return personRepository.findAll();
	}
	
	@GetMapping("/{ID}")
	public Person get(@PathVariable String ID) {
		return personRepository.findByID(ID);
	}

}
