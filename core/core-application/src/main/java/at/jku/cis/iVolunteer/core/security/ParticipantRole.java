package at.jku.cis.iVolunteer.core.security;

import org.springframework.security.core.GrantedAuthority;

public enum ParticipantRole implements GrantedAuthority {
	HELP_SEEKER, VOLUNTEER, FLEXPROD, RECRUITER, ADMIN, NONE;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}
}
