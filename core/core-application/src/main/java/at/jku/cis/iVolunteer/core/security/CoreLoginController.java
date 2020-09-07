package at.jku.cis.iVolunteer.core.security;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.global.GlobalInfo;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.model.ErrorResponse;
import at.jku.cis.iVolunteer.core.security.model.RefreshTokenResponse;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_HEADER_STRING;

@RestController
@RequestMapping("/login")
public class CoreLoginController {
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private CoreUserRepository userRepository;

	private JWTTokenProvider tokenProvider = new JWTTokenProvider();

	@GetMapping
	public CoreUser getLoggedInUser() {
		final CoreUser user = loginService.getLoggedInUser();
		return user;
	}
	
	@PutMapping("/activation-status")
	public boolean checkActivationStatus(@RequestBody String username) {
		final CoreUser user = userRepository.findByUsername(username);
		return (user != null && user.isActivated()) || user == null;
	}
	
	@GetMapping("/refreshToken")
	public ResponseEntity<Object> refreshToken(@RequestHeader(REFRESH_HEADER_STRING) String rawRefreshToken)
			throws Exception {
		try {
			if (StringUtils.hasText(rawRefreshToken) && this.tokenProvider.validateRefreshToken(rawRefreshToken)) {
				String refreshToken = rawRefreshToken.substring(7, rawRefreshToken.length());

				String userName = this.tokenProvider.getUserNameFromRefreshToken(refreshToken);
				at.jku.cis.iVolunteer.model.user.User iVolUser = this.userRepository.findByUsername(userName);

				org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
						iVolUser.getUsername(), iVolUser.getPassword(), Collections.emptyList());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				String accessToken = this.tokenProvider.generateAccessToken(authentication);

				return ResponseEntity.ok(new RefreshTokenResponse(accessToken));
			} else {
				return new ResponseEntity<Object>(new ErrorResponse("Empty Refresh Token"), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/globalInfo/role/{role}")
	public GlobalInfo getGlobalInfo(@PathVariable("role") UserRole role, @RequestBody List<String> tenantIds) {
		GlobalInfo globalInfo = new GlobalInfo();

		CoreUser user = loginService.getLoggedInUser();

		if (user != null) {
			globalInfo.setUser(user);
		}

		if (role != null) {
			globalInfo.setUserRole(role);
		}

		if (tenantIds.size() > 0) {
			if (role == UserRole.VOLUNTEER) {
				globalInfo.setTenants(user.getSubscribedTenants().stream()
						.filter(s -> s.getRole() == UserRole.VOLUNTEER).map(s -> s.getTenantId())
						.map(id -> this.tenantService.getTenantById(id)).collect((Collectors.toList())));
			} else {
				globalInfo.setTenants(tenantIds.stream().map(id -> this.tenantService.getTenantById(id))
						.collect(Collectors.toList()));
			}
		}

		final List<String> registeredMarketplaceIds = user.getRegisteredMarketplaceIds();
		if (registeredMarketplaceIds.size() > 0) {
			globalInfo.setMarketplace(this.marketplaceRepository.findOne(registeredMarketplaceIds.get(0)));
		}

		return globalInfo;
	}
}
