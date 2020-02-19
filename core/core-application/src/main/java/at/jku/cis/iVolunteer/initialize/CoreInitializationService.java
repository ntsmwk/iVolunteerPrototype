package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.flexprod.CoreFlexProdRepository;
import at.jku.cis.iVolunteer.core.recruiter.CoreRecruiterRepository;
import at.jku.cis.iVolunteer.core.tenant.CoreTenantRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;
import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;
import at.jku.cis.iVolunteer.model.core.user.CoreTenant;

@Service
public class CoreInitializationService {
	private static final String RECRUITER = "recruiter";
	private static final String FLEXPROD = "flexprod";
	private static final String RAW_PASSWORD = "passme";

	private static final String FFEIDENBERG = "FF_Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "Musikverein_Schwertberg";
	private static final String RKWILHERING = "RK_Wilhering";

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreRecruiterRepository coreRecruiterRepository;
	@Autowired private CoreFlexProdRepository coreFlexProdRepository;
	@Autowired private CoreTenantRepository coreTenantRepository;

	@Autowired private CoreVolunteerInitializationService coreVolunteerInitializationService;
	@Autowired private CoreHelpSeekerInitializationService coreHelpSeekerInitializationService;

	public void init() {
		createTenant(FFEIDENBERG);
		createTenant(MUSIKVEREINSCHWERTBERG);
		createTenant(RKWILHERING);

		createFlexProdUser(FLEXPROD, RAW_PASSWORD);

		coreVolunteerInitializationService.initVolunteers();
		coreHelpSeekerInitializationService.initHelpSeekers();

		createRecruiter(RECRUITER, RAW_PASSWORD, "Daniel", "Huber", "Recruiter");

	}

	private void createRecruiter(String username, String password, String firstName, String lastName,
			String position) {
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

	private CoreTenant createTenant(String name) {
		CoreTenant tenant = coreTenantRepository.findByName(name);

		if (tenant == null) {
			tenant = new CoreTenant();
			tenant.setName(name);

			tenant = coreTenantRepository.insert(tenant);
		}
		return tenant;

	}
}
