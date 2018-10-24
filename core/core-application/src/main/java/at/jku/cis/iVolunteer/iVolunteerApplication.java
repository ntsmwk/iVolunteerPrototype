package at.jku.cis.iVolunteer;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.SecurityConstants;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.mapper.core.user.CoreHelpSeekerMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootApplication
public class iVolunteerApplication {

	private static final String MARKETPLACE_ID = "0eaf3a6281df11e8adc0fa7ae01bbebc";
	private static final String MARKETPLACE_NAME = "Marketplace 1";
	private static final String MARKETPLACE_SHORTNAME = "MP1";
	private static final String MARKETPLACE_URL = "http://localhost:8080";

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	// private static final String AUTHORIZATION = "Bearer
	// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtbXVzdGVybWFubiIsInVzZXJuYW1lIjoibW11c3Rlcm1hbm4iLCJhdXRob3JpdGllcyI6WyJIRUxQX1NFRUtFUiJdLCJleHAiOjE1MzgzOTQ4MDJ9.lhe39ukOhsrxzKw_5X4cXsyqBu8Z0ukZciaQrhsTCG3H0yMIxMb4chlVWv_A3vibBDnKqWn8NMhUkEg7AWy0CQ";

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreMarketplaceRestClient marketplaceRestClient;
	@Autowired private CoreHelpSeekerMapper helpSeekerMapper;

	public static void main(String[] args) {
		SpringApplication.run(iVolunteerApplication.class, args);
	}

	@PostConstruct
	private void init() {
		Marketplace marketplace = createMarketplace();
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD, marketplace);
		createVolunteer(BROISER, RAW_PASSWORD);
		createVolunteer(PSTARZER, RAW_PASSWORD);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD);

	}

	private Marketplace createMarketplace() {
		Marketplace marketplace = marketplaceRepository.findOne(MARKETPLACE_ID);
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setId(MARKETPLACE_ID);
			marketplace.setUrl(MARKETPLACE_URL);
			marketplace.setName(MARKETPLACE_NAME);
			marketplace.setShortName(MARKETPLACE_SHORTNAME);
			marketplaceRepository.insert(marketplace);
		}
		return marketplace;
	}

	private CoreHelpSeeker createHelpSeeker(String username, String password, Marketplace marketplace) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
//			helpSeeker.getRegisteredMarketplaces().clear();
//			helpSeeker.getRegisteredMarketplaces().add(marketplace);
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
//			marketplaceRestClient.registerHelpSeeker(MARKETPLACE_URL, token, helpSeekerMapper.toDTO(helpSeeker));
		}
		return helpSeeker;
	}

	private CoreVolunteer createVolunteer(String username, String password) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
		return volunteer;
	}

//	private String createJWT(String subject) {
//		// The JWT signature algorithm we will be using to sign the token
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
//
//		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.SECRET);
//		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//		HashMap<String, Object> claims = new HashMap<>();
//		claims.put("sub", subject);
//		claims.put("username", subject);
//		claims.put("authorities", new String[] { "HELP_SEEKER" });
//
//		// @formatter:off
//		JwtBuilder builder = Jwts.builder()
//									.setIssuedAt(new Date(System.currentTimeMillis()))
//									.setClaims(claims)
//									.signWith(signatureAlgorithm, signingKey);
//		// @formatter:on
//
//		long expMillis = System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME;
//		Date exp = new Date(expMillis);
//		builder.setExpiration(exp);
//
//		return SecurityConstants.TOKEN_PREFIX + builder.compact();
//	}
}
