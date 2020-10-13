package at.jku.cis.iVolunteer.model.user;

import java.util.Objects;

public class NextcloudCredentials {
    private String domain;
    private String username;
    private String password;

    public NextcloudCredentials() {
    }

    public NextcloudCredentials(String domain, String username, String password) {
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NextcloudCredentials domain(String domain) {
        this.domain = domain;
        return this;
    }

    public NextcloudCredentials username(String username) {
        this.username = username;
        return this;
    }

    public NextcloudCredentials password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NextcloudCredentials)) {
            return false;
        }
        NextcloudCredentials nextcloudCredentials = (NextcloudCredentials) o;
        return Objects.equals(domain, nextcloudCredentials.domain)
                && Objects.equals(username, nextcloudCredentials.username)
                && Objects.equals(password, nextcloudCredentials.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, username, password);
    }

    @Override
    public String toString() {
        return "{" + " domain='" + getDomain() + "'" + ", username='" + getUsername() + "'" + ", password='"
                + getPassword() + "'" + "}";
    }

}
