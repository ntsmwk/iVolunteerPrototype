package at.jku.cis.iVolunteer.api.standard.model.personrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iVolunteer/PersonRoles")
public class PersonRoleController {

	@Autowired private PersonRoleService personRoleService;

	@PutMapping
	public void savePersonRole(List<PersonRole> roles) {
		personRoleService.savePersonRoles(roles);
	}
}
