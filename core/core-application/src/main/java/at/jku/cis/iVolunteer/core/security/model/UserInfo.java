package at.jku.cis.iVolunteer.core.security.model;

import java.util.Date;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.AccountType;
import at.jku.cis.iVolunteer.model.user.LocalRepositoryLocation;
import at.jku.cis.iVolunteer.model.user.NextcloudCredentials;

public class UserInfo {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String loginEmail;
    private String titleBefore;
    private String titleAfter;
    private Date birthday;
    private String organizationName;

    private LocalRepositoryLocation localRepositoryLocation;
    private String dropboxToken;
    private NextcloudCredentials nextcloudCredentials;

    private boolean activated;
    private AccountType accountType;

    public UserInfo() {
    }

    public UserInfo(CoreUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.loginEmail = user.getLoginEmail();
        this.titleBefore = user.getTitleBefore();
        this.titleAfter = user.getTitleAfter();
        this.birthday = user.getBirthday();
        this.organizationName = user.getOrganizationName();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLoginEmail() {
        return this.loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getTitleBefore() {
        return this.titleBefore;
    }

    public void setTitleBefore(String titleBefore) {
        this.titleBefore = titleBefore;
    }

    public String getTitleAfter() {
        return this.titleAfter;
    }

    public void setTitleAfter(String titleAfter) {
        this.titleAfter = titleAfter;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public LocalRepositoryLocation getLocalRepositoryLocation() {
        return this.localRepositoryLocation;
    }

    public void setLocalRepositoryLocation(LocalRepositoryLocation localRepositoryLocation) {
        this.localRepositoryLocation = localRepositoryLocation;
    }

    public String getDropboxToken() {
        return this.dropboxToken;
    }

    public void setDropboxToken(String dropboxToken) {
        this.dropboxToken = dropboxToken;
    }

    public NextcloudCredentials getNextcloudCredentials() {
        return this.nextcloudCredentials;
    }

    public void setNextcloudCredentials(NextcloudCredentials nextcloudCredentials) {
        this.nextcloudCredentials = nextcloudCredentials;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean getActivated() {
        return this.activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
