package at.jku.cis.iVolunteer.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LocalRepositoryLocation {
    LOCAL("LOCAL"), DROPBOX("DROPBOX"), NEXTCLOUD("NEXTCLOUD");

    private String value;

    LocalRepositoryLocation(String value) {
        this.value = value;
    }

    @JsonValue
    public String getName() {
        return value;
    }
}
