package at.jku.cis.iVolunteer.model.user;

import java.util.List;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

public class XUserInfo {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    // private String password; // TODO rly?
    private String titleBefore;
    private String titleAfter;
    private Address address;
    private List<String> phoneNumbers;
    private List<String> emails;
    private String profileImageFilename; // TODO Markus, TODO Philipp

    public XUserInfo() {
    }

    public XUserInfo(CoreUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.userName = user.getUsername();
        this.titleBefore = user.getTitleBefore();
        this.titleAfter = user.getTitleAfter();
        this.address = user.getAddress();
        this.phoneNumbers = user.getPhoneNumbers();
        this.emails = user.getEmails();
        // this.profileImageFilename = user.profileImageFilename(); // TODO Markus, TODO
        // Philipp

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return this.lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
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

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return this.emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getProfileImageFilename() {
        return this.profileImageFilename;
    }

    public void setProfileImageFilename(String profileImageFilename) {
        this.profileImageFilename = profileImageFilename;
    }

}