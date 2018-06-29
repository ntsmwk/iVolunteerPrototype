package at.jku.cis.iVolunteer.core.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController("/employee")
public class CoreEmployeeController {

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;

	@GetMapping("/{coreEmployeeId}")
	public CoreEmployee getCoreEmployee(@PathVariable("coreEmployeeId") String coreEmployeeId) {
		return coreEmployeeRepository.findOne(coreEmployeeId);
	}

	@GetMapping("/{coreEmployeeId}/marketplaces")
	public List<Marketplace> getRegisteredMarketplaces(@PathVariable("coreEmployeeId") String coreEmployeeId) {
		return coreEmployeeRepository.findOne(coreEmployeeId).getRegisteredMarketplaces();
	}
}
