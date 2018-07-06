package at.jku.cis.iVolunteer.core.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.participant.CoreEmployeeMapper;
import at.jku.cis.iVolunteer.mapper.marketplace.MarketplaceMapper;
import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.participant.dto.CoreEmployeeDTO;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;

@RestController
@RequestMapping("/employee")
public class CoreEmployeeController {

	@Autowired
	private CoreEmployeeMapper coreEmployeeMapper;

	@Autowired
	private MarketplaceMapper marketplaceMapper;

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;

	@GetMapping("/{coreEmployeeId}")
	public CoreEmployeeDTO getCoreEmployee(@PathVariable("coreEmployeeId") String coreEmployeeId) {
		return coreEmployeeMapper.toDTO(coreEmployeeRepository.findOne(coreEmployeeId));
	}

	@GetMapping("/{coreEmployeeId}/marketplace")
	public MarketplaceDTO getRegisteredMarketplaces(@PathVariable("coreEmployeeId") String coreEmployeeId) {
		CoreEmployee volunteer = coreEmployeeRepository.findOne(coreEmployeeId);
		return marketplaceMapper.toDTO(volunteer.getRegisteredMarketplaces().get(0));
	}

}
