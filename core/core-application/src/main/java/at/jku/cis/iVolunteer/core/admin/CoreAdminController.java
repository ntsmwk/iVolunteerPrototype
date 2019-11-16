package at.jku.cis.iVolunteer.core.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.user.CoreAdmin;

@RestController
@RequestMapping("/admin")
public class CoreAdminController {

	@Autowired private CoreAdminRepository coreAdminRepository;

	@GetMapping("/{adminId}")
	public CoreAdmin getCoreVolunteer(@PathVariable("adminId") String adminId) {
		return coreAdminRepository.findOne(adminId);
	}

}
