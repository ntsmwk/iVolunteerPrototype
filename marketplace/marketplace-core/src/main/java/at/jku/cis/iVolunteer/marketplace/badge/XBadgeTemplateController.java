package at.jku.cis.iVolunteer.marketplace.badge;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.core.CoreStorageRestClient;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplateDTO;

@RestController
@RequestMapping("badgeTemplate")
public class XBadgeTemplateController {

	@Autowired private XBadgeTemplateRepository badgeTemplateRepository;
	@Autowired private CoreStorageRestClient coreStorageRestClient;

	@PostMapping("/tenant")
	public Map<String, List<XBadgeTemplate>> getBadgeTemplateByTenantId(@RequestBody List<String> tenantIds) {
		List<XBadgeTemplate> badgeTemplates = badgeTemplateRepository.findAll();
		Map<String, List<XBadgeTemplate>> map = badgeTemplates.stream()
				.filter(bt -> tenantIds.contains(bt.getTenantId()))
				.collect(Collectors.groupingBy(bt -> bt.getTenantId(), Collectors.toList()));

		return map;
	}

	@PostMapping("/new")
	public ResponseEntity<Object> createBadgeTemplate(@RequestBody XBadgeTemplateDTO badgeTemplateDTO,
			@RequestHeader("Authorization") String authorization) {
		
		ResponseEntity<StringResponse> responseEntity = coreStorageRestClient
				.storeImageBase64(UUID.randomUUID().toString(), badgeTemplateDTO.getImage(), authorization);
		XBadgeTemplate badgeTemplate = new XBadgeTemplate();
		badgeTemplate.setId(badgeTemplateDTO.getId());
		badgeTemplate.setName(badgeTemplateDTO.getName());
		badgeTemplate.setDescription(badgeTemplateDTO.getDescription());
		badgeTemplate.setTenantId(badgeTemplateDTO.getTenantId());
		badgeTemplate.setImagePath(responseEntity.getBody().getMessage());

		badgeTemplateRepository.save(badgeTemplate);

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/init")
	public ResponseEntity<Object> createBadgeTemplate(@RequestBody XBadgeTemplate badgeTemplate) {
		badgeTemplateRepository.save(badgeTemplate);
		return ResponseEntity.ok().build();
	}

}
