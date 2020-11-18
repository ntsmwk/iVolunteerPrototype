package at.jku.cis.iVolunteer.configurator.configurations.matching.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;

@RestController
public class MatchingConfigurationController {

	@Autowired private MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired private MatchingConfigurationService matchingConfigurationService;

	@GetMapping("matching-configuration/all")
	public List<MatchingConfiguration> getAllMatchingConfigurations() {
		return matchingConfigurationRepository.findAll();
	}
	
	@GetMapping("matching-configuration/all/tenant/{tenantId}")
	public List<MatchingConfiguration> getAllMatchingConfigurations(@PathVariable("tenantId") String tenantId) {
		return matchingConfigurationRepository.findByTenantId(tenantId);
	}
	
	@GetMapping("matching-configuration/{id}")
	public MatchingConfiguration getAllMatchingConfigurationsById(@PathVariable("id") String id) {
		return matchingConfigurationService.getMatchingConfigurationById(id);
	}

	@GetMapping("matching-configuration/by-class-configurators/{leftClassConfigurationId}/{rightClassConfigurationId}")
	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIds(
			@PathVariable("leftClassConfigurationId") String leftClassConfigurationId,
			@PathVariable("rightClassConfigurationId") String rightClassConfigurationId) {
		return matchingConfigurationService.getMatchingConfiguratorByClassConfigurationIds(leftClassConfigurationId,
				rightClassConfigurationId);
	}

	@GetMapping("matching-configuration/by-class-configurators/{classConfigurationId1}/{classConfigurationId2}/unordered")
	public MatchingConfiguration getMatchingConfiguratorByClassConfigurationIdsUnordered(
			@PathVariable("classConfigurationId1") String classConfigurationId1,
			@PathVariable("classConfigurationId2") String classConfigurationId2) {
		return matchingConfigurationService
				.getMatchingConfiguratorByClassConfigurationIdsUnordered(classConfigurationId1, classConfigurationId2);
	}
	

	@PostMapping("matching-configuration/save")
	MatchingConfiguration saveMatchingConfiguration(@RequestBody MatchingConfiguration matchingConfiguration) {
		return matchingConfigurationService.saveMatchingConfiguration(matchingConfiguration);
	}

	@DeleteMapping("matching-configuration/{id}/delete")
	public void deleteMatchingConfiguration(@PathVariable("id") String id) {
		matchingConfigurationService.deleteMatchingConfiguration(id);
	}

	@PutMapping("matching-configuration/delete-multiple")
	public List<MatchingConfiguration> deleteMultipleMatchingConfigurations(@RequestBody() List<String> ids) {
		ids.forEach(this.matchingConfigurationRepository::delete);
		return this.matchingConfigurationRepository.findAll();
	}

}
