package at.jku.cis.iVolunteer.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	VOLUNTEER, HELP_SEEKER, TENANT_ADMIN, FLEXPROD, RECRUITER, ADMIN, NONE, ROLE_TEST;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}
}
