package at.jku.cis.iVolunteer.core.security;

import org.springframework.security.core.GrantedAuthority;

public enum ParticipantRole implements GrantedAuthority {
	ADMIN, HELP_SEEKER, VOLUNTEER;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}
}
