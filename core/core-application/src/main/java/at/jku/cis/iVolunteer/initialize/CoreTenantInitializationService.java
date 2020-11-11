package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.file.StorageService;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@Service
public class CoreTenantInitializationService {

	private static final String FF_EIDENBERG = "FF Eidenberg";
	private static final String MV_SCHWERTBERG = "MV Schwertberg";
	private static final String RK_WILHERING = "RK Wilhering";
	private static final String FLEXPROD = "FlexProd";

	@Autowired
	protected TenantRepository coreTenantRepository;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private StorageService storageService;
	@Value("${spring.data.server.uri}")
	private String serverUrl;

	public void initTenants() {
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		if (marketplace != null) {
			createTenant(FF_EIDENBERG, "www.ff-eidenberg.at", "/img/FF_Altenberg.jpg", "/img/FF_Eidenberg.png",
					"#b20000", "#b2b2b2", "Freiwillige Feuerwehr Eidenberg", marketplace.getId());
			createTenant(MV_SCHWERTBERG, "www.musikverein-schwertberg.at", "/img/musikvereinschwertberg.jpeg",
					"/img/musicverein.jpg", "#005900", "#b2b2b2", "Musikverein Schwertberg", marketplace.getId());
			createTenant(RK_WILHERING,
					"www.roteskreuz.at/ooe/dienststellen/eferding/die-bezirksstelle/die-ortsstellen/wilhering",
					"/img/OERK_Sonderlogo_rgb_cropped.jpg", "", "#b2b2b2", "#b2b2b2", "Rotes Kreuz",
					marketplace.getId());
		}
	}

	public void initFlexprodTenant() {
		Marketplace marketplace = marketplaceRepository.findByName("Marketplace 1");
		if (marketplace != null) {
			createTenant(FLEXPROD, "www.flexprod.at", "", "", "#b2b2b2", "#b2b2b2", "FlexProd", marketplace.getId());
		}
	}

	private Tenant createTenant(String name, String homepage, String profileImageFilename,
			String landingPageImageFilename, String primaryColor, String secondaryColor, String landingpageTitle,
			String marketplaceId) {
		Tenant tenant = coreTenantRepository.findByName(name);

		if (tenant == null) {
			tenant = new Tenant();
			tenant.setName(name);
			tenant.setHomepage(homepage);
			setTenantProfileImage(profileImageFilename, tenant);
			setTenantLandingPageImage(landingPageImageFilename, tenant);
			tenant.setPrimaryColor(primaryColor);
			tenant.setSecondaryColor(secondaryColor);
			tenant.setMarketplaceId(marketplaceId);
			tenant.setLandingpageMessage("Herzlich Willkommen bei iVolunteer!");
			tenant.setLandingpageTitle(landingpageTitle);
			tenant = coreTenantRepository.insert(tenant);
		}
		return tenant;
	}

	private void setTenantProfileImage(String fileName, Tenant tenant) {
		if (fileName != null && !fileName.equals("")) {
			ClassPathResource classPathResource = new ClassPathResource(fileName);
			String fileUrl = storageService.store(classPathResource);
			tenant.setImagePath(fileUrl);
		}
	}

	private void setTenantLandingPageImage(String fileName, Tenant tenant) {
		if (fileName != null && !fileName.equals("")) {
			ClassPathResource classPathResource = new ClassPathResource(fileName);
			String fileUrl = storageService.store(classPathResource);
			tenant.setLandingpageImagePath(fileUrl);
		}
	}
}
