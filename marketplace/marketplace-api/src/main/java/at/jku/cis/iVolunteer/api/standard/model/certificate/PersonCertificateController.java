package at.jku.cis.iVolunteer.api.standard.model.certificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/PersonCertificates")
public class PersonCertificateController {

	@Autowired private PersonCertificateService personCertificateService;

	@PutMapping("/tenant/{tenantId}")
	public void savePersonCertificates(@RequestBody List<PersonCertificate> certificates, @PathVariable("tenantId") String tenantId) {
		personCertificateService.savePersonCertificate(certificates, tenantId);
	}

}
