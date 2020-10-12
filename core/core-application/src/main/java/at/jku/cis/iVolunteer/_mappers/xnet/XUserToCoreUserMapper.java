package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.XUser;

@Component
public class XUserToCoreUserMapper implements AbstractMapper<XUser, CoreUser> {

    @Override
    public CoreUser toTarget(XUser source) {
        if (source == null) {
            return null;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setId(source.getId());
        coreUser.setFirstname(source.getFirstname());
        coreUser.setLastname(source.getLastname());
        coreUser.setUsername(source.getUsername());
        coreUser.setTitleBefore(source.getTitleBefore());
        coreUser.setTitleAfter(source.getTitleAfter());
        // user.setAddress(source.getAddress()); // TODO Alexander, Philipp:
        // Address mapper
        coreUser.setBirthday(source.getBirthDate());
        coreUser.setPhoneNumbers(source.getPhoneNumbers());
        coreUser.setEmails(source.getEmails());
        coreUser.setProfileImagePath(source.getProfileImagePath());

        return coreUser;
    }

    @Override
    public List<CoreUser> toTargets(List<XUser> sources) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XUser toSource(CoreUser target) {
        if (target == null) {
            return null;
        }

        XUser xUser = new XUser();
        xUser.setId(target.getId());
        xUser.setFirstname(target.getFirstname());
        xUser.setLastname(target.getLastname());
        xUser.setUsername(target.getUsername());
        xUser.setTitleBefore(target.getTitleBefore());
        xUser.setTitleAfter(target.getTitleAfter());
        // xUser.setAddress(target.getAddress()); // TODO Alexander, Philipp:
        // Address mapper
        xUser.setBirthDate(target.getBirthday());
        xUser.setPhoneNumbers(target.getPhoneNumbers());
        xUser.setEmails(target.getEmails());
        xUser.setProfileImagePath(target.getProfileImagePath());

        return xUser;
    }

    @Override
    public List<XUser> toSources(List<CoreUser> targets) {
        // TODO Auto-generated method stub
        return null;
    }

}
