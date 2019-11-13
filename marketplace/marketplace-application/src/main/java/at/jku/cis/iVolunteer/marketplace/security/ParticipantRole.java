package at.jku.cis.iVolunteer.marketplace.security;

import org.springframework.security.core.GrantedAuthority;

public enum ParticipantRole implements GrantedAuthority {
	HELP_SEEKER, VOLUNTEER, RECRUITER;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}
}
