package at.jku.cis.iVolunteer.core.badge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@RestController
public class XCoreBadgeTemplateController {

	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private XBadgeTemplateRestClient badgeTemplateRestClient;

	@PostMapping
	public Map<String, List<XBadgeTemplate>> getBadgeTemplatesByTenantIds(@RequestBody List<String> tenantIds, @RequestHeader("Authorization") String authorization){
		Map<String, List<XBadgeTemplate>> xBadgeTemplates = new HashMap<>();
		
		List<Marketplace> marketplaces = marketplaceRepository.findAll();
		
		for(Marketplace marketplace : marketplaces) {
			Map<String, List<XBadgeTemplate>> temp = badgeTemplateRestClient.getXBadgeTemplates(marketplace.getUrl(), tenantIds, authorization);
			xBadgeTemplates.putAll(temp);
		}
		
		return xBadgeTemplates;
	}
}
