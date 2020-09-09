package at.jku.cis.iVolunteer.core.security.model;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;

public class RefreshTokenResponse {

    private String accessToken;

    public RefreshTokenResponse(String accessToken) {
        this.accessToken = TOKEN_PREFIX + accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
