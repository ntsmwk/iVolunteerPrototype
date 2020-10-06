package at.jku.cis.iVolunteer.core.security;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.global.GlobalInfo;
import at.jku.cis.iVolunteer.core.global.UserSubscriptionDTO;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.security.model.ErrorResponse;
import at.jku.cis.iVolunteer.core.security.model.TokenResponse;
import at.jku.cis.iVolunteer.core.security.model.UserInfo;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;;

@RestController
public class CoreLoginController {
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MarketplaceService marketplaceService;
	@Autowired
	private CoreLoginService loginService;
	@Autowired
	private CoreUserRepository userRepository;

	private JWTTokenProvider tokenProvider = new JWTTokenProvider();

	@GetMapping("/userinfo")
	public UserInfo getUserInfo() {
		CoreUser user = loginService.getLoggedInUser();
		return new UserInfo(user);
	}

	@PutMapping("/activation-status")
	public boolean checkActivationStatus(@RequestBody String username) {
		final CoreUser user = userRepository.findByUsername(username);
		return (user != null && user.isActivated()) || user == null;
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<Object> refreshToken(@RequestBody String rawRefreshToken) throws Exception {
		try {
			if (StringUtils.hasText(rawRefreshToken) && this.tokenProvider.validateRefreshToken(rawRefreshToken)) {
				String refreshToken = rawRefreshToken.substring(TOKEN_PREFIX.length(), rawRefreshToken.length());

				String username = this.tokenProvider.getUserNameFromRefreshToken(refreshToken);
				CoreUser iVolUser = this.userRepository.findByUsername(username);
				User user = new User(iVolUser.getUsername(), iVolUser.getPassword(), Collections.emptyList());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				String accessToken = this.tokenProvider.generateAccessToken(authentication);
				String newRefreshToken = this.tokenProvider.generateRefreshToken(authentication);

				return ResponseEntity.ok(new TokenResponse(accessToken, newRefreshToken).toString());
			} else {
				return new ResponseEntity<Object>(new ErrorResponse("Empty Refresh Token"), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/globalInfo")
	public GlobalInfo getGlobalInfo() {
		CoreUser user = loginService.getLoggedInUser();

		List<Marketplace> marketplaces = user.getRegisteredMarketplaceIds().stream()
				.map(id -> marketplaceService.findById(id)).collect(Collectors.toList());

		List<UserSubscriptionDTO> subscriptions = new ArrayList<UserSubscriptionDTO>();
		user.getSubscribedTenants().forEach(subscription -> {
			UserSubscriptionDTO s = new UserSubscriptionDTO(
					marketplaceService.findById(subscription.getMarketplaceId()),
					tenantService.getTenantById(subscription.getTenantId()), subscription.getRole());
			subscriptions.add(s);
		});

		GlobalInfo globalInfo = new GlobalInfo(new UserInfo(user), subscriptions, marketplaces);
		return globalInfo;
	}
}
