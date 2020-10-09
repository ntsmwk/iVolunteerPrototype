package at.jku.cis.iVolunteer.model.user;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Component
public class XUserInfoMapper {

    public CoreUser mapToUser(XUserInfo userInfo) {
        CoreUser user = new CoreUser();

        user.setId(userInfo.getId());
        user.setFirstname(userInfo.getFirstname());
        user.setLastname(userInfo.getLastname());
        user.setUsername(userInfo.getUsername());
        user.setTitleBefore(userInfo.getTitleBefore());
        user.setTitleAfter(userInfo.getTitleAfter());
        user.setAddress(userInfo.getAddress());
        user.setPhoneNumbers(userInfo.getPhoneNumbers());
        user.setEmails(userInfo.getEmails());
        // TODO Markus: profileImageFilename

        return user;
    }

}
