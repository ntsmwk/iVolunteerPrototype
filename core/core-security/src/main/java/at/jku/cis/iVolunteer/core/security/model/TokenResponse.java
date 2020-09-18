package at.jku.cis.iVolunteer.core.security.model;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;

import com.google.gson.JsonObject;

public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = TOKEN_PREFIX + accessToken;
        this.refreshToken = TOKEN_PREFIX + refreshToken;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.addProperty("accessToken", accessToken);
        json.addProperty("refreshToken", refreshToken);
        return json.toString();
    }

}
