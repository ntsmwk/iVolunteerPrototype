package at.jku.cis.iVolunteer.api.standard.model.certificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iVolunteer/PersonCertificates")
public class PersonCertificateController {

	@Autowired
	private PersonCertificateRepository personCertificateRepository;

	@GetMapping()
	public List<PersonCertificate> getPersonCertificates() {
		return personCertificateRepository.findAll();
	}

	@GetMapping("/{ID}")
	public List<PersonCertificate> getPersonCertificates(@PathVariable String ID) {
		return personCertificateRepository.findByPersonID(ID);
	}

}
