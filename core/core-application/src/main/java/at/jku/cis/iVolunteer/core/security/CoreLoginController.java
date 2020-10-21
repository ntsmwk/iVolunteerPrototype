package at.jku.cis.iVolunteer.core.security;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.global.GlobalInfo;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.security.model.TokenResponse;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.security.RefreshToken;
import at.jku.cis.iVolunteer.model.user.UserRole;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;;

@RestController
public class CoreLoginController {
	@Autowired private TenantService tenantService;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreLoginService loginService;
	@Autowired private CoreUserRepository userRepository;

	private JWTTokenProvider tokenProvider = new JWTTokenProvider();

	@GetMapping("/user")
	public ResponseEntity<Object> getLoggedInUser() {
		CoreUser user = loginService.getLoggedInUser();

		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse("user does not exist"), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user);
	}

	@PutMapping("/activation-status")
	public boolean checkActivationStatus(@RequestBody String username) {
		final CoreUser user = userRepository.findByUsername(username);
		return (user != null && user.isActivated()) || user == null;
	}

	@PostMapping("/auth/refreshToken")
	public ResponseEntity<Object> refreshToken(@RequestBody RefreshToken rawRefreshToken) throws Exception {
		try {
			if (StringUtils.hasText(rawRefreshToken.getRefreshToken())
					&& this.tokenProvider.validateRefreshToken(rawRefreshToken.getRefreshToken())) {
				String refreshToken = rawRefreshToken.getRefreshToken().substring(TOKEN_PREFIX.length(),
						rawRefreshToken.getRefreshToken().length());

				String username = this.tokenProvider.getUserNameFromRefreshToken(refreshToken);
				CoreUser iVolUser = this.userRepository.findByUsername(username);
				User user = new User(iVolUser.getUsername(), iVolUser.getPassword(), Collections.emptyList());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				String accessToken = this.tokenProvider.generateAccessToken(authentication);
				String newRefreshToken = this.tokenProvider.generateRefreshToken(authentication);

				return ResponseEntity.ok(new TokenResponse(accessToken, newRefreshToken).toString());
			} else {
				return new ResponseEntity<Object>(new ErrorResponse("Empty, malformed, or invalid Refresh Token"),
						HttpStatus.NOT_ACCEPTABLE);
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
