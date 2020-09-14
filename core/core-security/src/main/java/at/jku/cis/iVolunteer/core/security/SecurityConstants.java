package at.jku.cis.iVolunteer.core.security;

public interface SecurityConstants {
	public static final String ACCESS_SECRET = "SecretKeyToGenAccessJWTs";
	public static final String REFRESH_SECRET = "SecretKeyToGenRefreshJWTs";

	public static final long ACCES_EXPIRATION_TIME = 900_000; // 15 min
	public static final long REFRESH_EXPIRATION_TIME = 864_000_000; // 10 days

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String ACCESS_HEADER_STRING = "Authorization";
	public static final String REFRESH_HEADER_STRING = "Refresh";

	public static final String JWT_USERNAME = "username";
	public static final String JWT_AUTHORITIES = "authorities";
}