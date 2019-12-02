package at.jku.cis.iVolunteer.api.standard.model.certificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iVolunteer/PersonCertificates")
public class PersonCertificateController {

	@Autowired private PersonCertificateService personCertificateService;

	@PutMapping
	public void savePersonCertificates(List<PersonCertificate> certificates) {
		personCertificateService.savePersonCertificate(certificates);
	}

}
