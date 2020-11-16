package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplateDTO;

@RestController
@RequestMapping("badgeTemplate")
public class XBadgeTemplateController {

	@Autowired private XBadgeTemplateRepository badgeTemplateRepository;

	@PostMapping("/tenant")
	public Map<String, List<XBadgeTemplate>> getBadgeTemplateByTenantId(@RequestBody List<String> tenantIds) {
		List<XBadgeTemplate> badgeTemplates = badgeTemplateRepository.findAll();
		Map<String, List<XBadgeTemplate>> map = badgeTemplates.stream()
				.filter(bt -> tenantIds.contains(bt.getTenant().getId()))
				.collect(Collectors.groupingBy(bt -> bt.getTenant().getId(), Collectors.toList()));

		return map;
	}
	
	@PostMapping("/new")
	public ResponseEntity<Object> createBadgeTemplate(@RequestBody XBadgeTemplateDTO badgeTemplateDTO){
		
		
		
		return ResponseEntity.ok().build();
	}
}
