package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

import java.util.Collections;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.XUser;

@Component
public class XCoreUserMapper implements AbstractMapper<CoreUser, XUser> {

    @Override
    public XUser toTarget(CoreUser source) {
        if (source == null) {
            return null;
        }

        XUser xUser = new XUser();
        xUser.setId(source.getId());
        xUser.setFirstName(source.getFirstname());
        xUser.setLastName(source.getLastname());
        xUser.setUsername(source.getUsername());
        xUser.setTitleBefore(source.getTitleBefore());
        xUser.setTitleAfter(source.getTitleAfter());
        // xUser.setAddress(source.getAddress()); // TODO Alexander, Philipp:
        // Address mapper
        xUser.setBirthDate(source.getBirthday());
        xUser.setPhoneNumbers(source.getPhoneNumbers());
        xUser.setEmail(source.getLoginEmail());
        xUser.setProfileImagePath(source.getProfileImagePath());

        return xUser;
    }

    @Override
    public List<XUser> toTargets(List<CoreUser> sources) {
    	return sources.stream().map(u -> this.toTarget(u)).collect(Collectors.toList());
    }

    @Override
    public CoreUser toSource(XUser target) {
        if (target == null) {
            return null;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setId(target.getId());
        coreUser.setFirstname(target.getFirstName());
        coreUser.setLastname(target.getLastName());
        coreUser.setUsername(target.getUsername());
        coreUser.setTitleBefore(target.getTitleBefore());
        coreUser.setTitleAfter(target.getTitleAfter());
        // user.setAddress(target.getAddress()); // TODO Alexander, Philipp:
        // Address mapper
        coreUser.setBirthday(target.getBirthDate());
        coreUser.setPhoneNumbers(target.getPhoneNumbers());
        coreUser.setLoginEmail(target.getEmail());
        coreUser.setEmails(asList(target.getEmail()));
        coreUser.setProfileImagePath(target.getProfileImagePath());
        return coreUser;
    }

    @Override
    public List<CoreUser> toSources(List<XUser> targets) {
    	return targets.stream().map(u -> this.toSource(u)).collect(Collectors.toList());
    }

}
