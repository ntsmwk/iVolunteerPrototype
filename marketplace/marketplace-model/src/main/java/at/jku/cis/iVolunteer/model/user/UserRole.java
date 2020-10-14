package at.jku.cis.iVolunteer.model.user;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole implements GrantedAuthority {
	VOLUNTEER, HELP_SEEKER, TENANT_ADMIN, FLEXPROD, RECRUITER, ADMIN, NONE;

	@Override
	public String getAuthority() {
		return name().toUpperCase();
	}

	private final String role;

	private UserRole() {
		this.role = name().toUpperCase();
	}

	public String getUserRole() {
		return this.role;
	}

	@Override
	public String toString() {
		return role;
	}

	@JsonCreator
	public static UserRole getUserRole(String role) {
		for (UserRole r : UserRole.values()) {
			if (r.getUserRole().equals(role)) {
				return r;
			}
		}
		throw new IllegalArgumentException();
	}
}
