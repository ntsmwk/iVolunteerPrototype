package at.jku.cis.iVolunteer.initialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.admin.CoreAdminRepository;
import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.tenant.CoreTenantRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreAdmin;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;

@Service
public class CoreInitializationService {
	private static final String RECRUITER = "recruiter";
	private static final String FLEXPROD = "flexprod";
	private static final String ADMIN = "admin";
	private static final String RAW_PASSWORD = "passme";

	private static final String FFEIDENBERG = "FF_Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "Musikverein_Schwertberg";
	private static final String RKWILHERING = "RK_Wilhering";

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private CoreAdminRepository coreAdminRepository;
	@Autowired private CoreTenantRepository coreTenantRepository;

	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;

	public void init() {
		createTenant(FFEIDENBERG, "img/FF_Altenberg.jpg", "#b20000", "#b2b2b2");
		createTenant(MUSIKVEREINSCHWERTBERG, "img/musikvereinschwertberg.jpeg", "#005900", "#b2b2b2");
		createTenant(RKWILHERING, "img/OERK_Sonderlogo_rgb_cropped.jpg", "#b2b2b2", "#b2b2b2");

		createFlexProdUser(FLEXPROD, RAW_PASSWORD);
		
		createAdminUser(ADMIN, RAW_PASSWORD);

		createRecruiter(RECRUITER, RAW_PASSWORD, "Daniel", "Huber", "Recruiter");

		coreVolunteerInitializationService.initVolunteers();
		coreHelpSeekerInitializationService.initHelpSeekers();
	}

	private void createRecruiter(String username, String password, String firstName, String lastName, String position) {
		CoreRecruiter recruiter = coreRecruiterRepository.findByUsername(username);
		if (recruiter == null) {
			recruiter = new CoreRecruiter();
			recruiter.setUsername(username);
			recruiter.setPassword(bCryptPasswordEncoder.encode(password));
			recruiter.setFirstname(firstName);
			recruiter.setLastname(lastName);
			recruiter.setPosition(position);
			recruiter = coreRecruiterRepository.insert(recruiter);
		}
	}

	private CoreAdmin createAdminUser(String username, String password) {
		CoreAdmin fpUser = coreAdminRepository.findByUsername(username);
		if (fpUser == null) {
			fpUser = new CoreAdmin();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			fpUser = coreAdminRepository.insert(fpUser);
		}
		return fpUser;
	}

	private CoreFlexProd createFlexProdUser(String username, String password) {

		CoreFlexProd fpUser = coreFlexProdRepository.findByUsername(username);

		if (fpUser == null) {
			fpUser = new CoreFlexProd();
			fpUser.setUsername(username);
			fpUser.setPassword(bCryptPasswordEncoder.encode(password));
			fpUser = coreFlexProdRepository.insert(fpUser);
		}

		return fpUser;
	}

	private Tenant createTenant(String name, String fileName, String primaryColor, String secondaryColor) {
		Tenant tenant = coreTenantRepository.findByName(name);

		if (tenant == null) {
			tenant = new Tenant();
			tenant.setName(name);
			setTenantImage(fileName, tenant);
			tenant.setPrimaryColor(primaryColor);
			tenant.setSecondaryColor(secondaryColor);
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
