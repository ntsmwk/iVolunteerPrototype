package at.jku.cis.iVolunteer.core.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.user.CoreAdminMapper;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreAdminDTO;

@RestController
@RequestMapping("/admin")
public class CoreAdminController {

	@Autowired 
	private CoreAdminMapper coreAdminMapper;
	@Autowired 
	private CoreAdminRepository coreAdminRepository;

	@GetMapping("/{adminId}")
	public CoreAdminDTO getCoreVolunteer(@PathVariable("adminId") String adminId) {
		return coreAdminMapper.toDTO(coreAdminRepository.findOne(adminId));
	}

}
