package at.jku.cis.iVolunteer.api.standard.model.personrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/PersonRoles")
public class PersonRoleController {

	@Autowired private PersonRoleService personRoleService;

	@PutMapping("/tenant/{tenantId}")
	public void savePersonRole(@RequestBody List<PersonRole> roles, @PathVariable("tenantId") String tenantId) {
		personRoleService.savePersonRoles(roles, tenantId);
	}
}
