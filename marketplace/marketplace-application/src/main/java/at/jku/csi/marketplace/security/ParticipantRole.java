package at.jku.csi.marketplace.security;

import org.springframework.security.core.GrantedAuthority;

public enum ParticipantRole implements GrantedAuthority {
	ORGANISATION, VOLUNTEER;

	@Override
	public String getAuthority() {
		return name();
	}
}
