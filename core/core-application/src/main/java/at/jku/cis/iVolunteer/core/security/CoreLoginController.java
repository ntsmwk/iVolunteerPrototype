package at.jku.cis.iVolunteer.core.security;

import java.util.List;
import java.util.stream.Collectors;
import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
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
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_HEADER_STRING;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/login")
public class CoreLoginController {
	@Autowired
	private TenantService tenantService;
	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreLoginService loginService;

	private JWTTokenProvider tokenProvider = new JWTTokenProvider();

	@GetMapping
	public CoreUser getLoggedInUser() {
		final CoreUser user = loginService.getLoggedInUser();

		return user;
	}

	@GetMapping("/refreshToken")
	public String refreshToken(@RequestHeader(REFRESH_HEADER_STRING) String authRefreshToken) throws Exception {
		// TODO Philipp: refreshToken verification necessary? handled via spring...
		try {
			if (StringUtils.hasText(authRefreshToken)) {
				String userName = "";
				if (StringUtils.hasText(authRefreshToken) && authRefreshToken.startsWith(TOKEN_PREFIX)) {
					String refreshJwt = authRefreshToken.substring(7, authRefreshToken.length());

					userName = this.tokenProvider.getUserNameFromRefreshToken(refreshJwt);

				} else {
					// error: Jwt is empty or Bearer missing
					// throw new AppException("Jwt is empty or Bearer missing",
					// ErrorCodes.UNNAUTHORIZED.toString());
				}

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName,
						null, emptyList());

				String accessJwtToken = this.tokenProvider.generateAccessToken(authentication);

				return accessJwtToken;
			} else
				return null;
		} catch (Exception ex) {
			// error: Could not set user authentication in security context
			return null;
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
