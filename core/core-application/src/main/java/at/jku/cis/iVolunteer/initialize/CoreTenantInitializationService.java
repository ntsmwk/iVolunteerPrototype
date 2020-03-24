package at.jku.cis.iVolunteer.initialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@Service
public class CoreTenantInitializationService {

	private static final String FF_EIDENBERG = "FF Eidenberg";
	private static final String MV_SCHWERTBERG = "MV Schwertberg";
	private static final String RK_WILHERING = "RK Wilhering";

	@Autowired private TenantRepository coreTenantRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;

	public void initTenants() {
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		if (marketplace != null) {
			createTenant(FF_EIDENBERG, "img/FF_Altenberg.jpg", "#b20000", "#b2b2b2", marketplace.getId());
			createTenant(MV_SCHWERTBERG, "img/musikvereinschwertberg.jpeg", "#005900", "#b2b2b2", marketplace.getId());
			createTenant(RK_WILHERING, "img/OERK_Sonderlogo_rgb_cropped.jpg", "#b2b2b2", "#b2b2b2",
					marketplace.getId());
		}
	}

	private Tenant createTenant(String name, String fileName, String primaryColor, String secondaryColor,
			String marketplaceId) {
		Tenant tenant = coreTenantRepository.findByName(name);

		if (tenant == null) {
			tenant = new Tenant();
			tenant.setName(name);
			setTenantImage(fileName, tenant);
			tenant.setPrimaryColor(primaryColor);
			tenant.setSecondaryColor(secondaryColor);
			tenant.setMarketplaceId(marketplaceId);
			tenant = coreTenantRepository.insert(tenant);
		}
		return tenant;
	}

	private void setTenantImage(String fileName, Tenant tenant) {
		if (fileName != null && !fileName.equals("")) {
			try {
				Resource resource = new ClassPathResource(fileName);
				File file = resource.getFile();
				tenant.setImage(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
