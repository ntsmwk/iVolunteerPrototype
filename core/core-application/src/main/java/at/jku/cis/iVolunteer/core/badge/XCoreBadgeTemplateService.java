package at.jku.cis.iVolunteer.core.badge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.file.StorageService;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@Service
public class XCoreBadgeTemplateService {

	@Autowired private StorageService storageService;
	@Autowired private XBadgeTemplateRestClient badgeTemplateRestClient;
	
	public void createBadgeTemplates(Marketplace marketplace, Tenant tenant) {
		createBadgeTemplate("Badge 1", "Badge 1 Description", "/img/badge1.png", tenant, marketplace);
		createBadgeTemplate("Badge 2", "Badge 2 Description", "/img/badge2.png", tenant, marketplace);
		createBadgeTemplate("Badge 3", "Badge 3 Description", "/img/badge3.png", tenant, marketplace);
		createBadgeTemplate("Badge 4", "Badge 4 Description", "/img/badge4.png", tenant, marketplace);
		createBadgeTemplate("Badge 5", "Badge 5 Description", "/img/badge5.png", tenant, marketplace);
		createBadgeTemplate("Badge 6", "Badge 6 Description", "/img/badge6.png", tenant, marketplace);
	}

	private void createBadgeTemplate(String name, String description, String fileName, Tenant tenant, Marketplace marketplace) {

		if (fileName != null && !fileName.equals("")) {
			ClassPathResource classPathResource = new ClassPathResource(fileName);
			String fileUrl = storageService.store(classPathResource);
			XBadgeTemplate badgeTemplate = new XBadgeTemplate();
			badgeTemplate.setName(name);
			badgeTemplate.setDescription(description);
			badgeTemplate.setTenantId(tenant.getId());
			badgeTemplate.setImagePath(fileUrl);
			badgeTemplateRestClient.createBadgeTemplate(marketplace.getUrl(), badgeTemplate);
		}

	}
}
