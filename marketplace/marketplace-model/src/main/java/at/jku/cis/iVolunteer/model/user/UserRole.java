package at.jku.cis.iVolunteer.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	HELP_SEEKER, VOLUNTEER, FLEXPROD, RECRUITER, ADMIN, NONE;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}
}
