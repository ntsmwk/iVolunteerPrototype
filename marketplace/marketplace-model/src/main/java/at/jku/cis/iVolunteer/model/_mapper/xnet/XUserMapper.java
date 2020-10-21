package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.Address;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.XAddress;
import at.jku.cis.iVolunteer.model.user.XUser;

@Component
public class XUserMapper {

 
    public XUser toTarget(User source) {
        if (source == null) {
            return null;
        }

        XUser user = new XUser();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setTitleBefore(source.getTitleBefore());
        user.setFirstName(source.getFirstname());
        user.setLastName(source.getLastname());
        user.setTitleAfter(source.getTitleAfter());
        user.setBirthDate(source.getBirthday());
        user.setAddress(new XAddress());// TODO
        user.setPhoneNumbers(source.getPhoneNumbers());
        user.setEmail(source.getLoginEmail());
        user.setProfileImagePath(source.getProfileImagePath());

        return user;
    }

    public List<XUser> toTargets(List<? extends User> sources) {
        if (sources == null) {
            return null;
        }
        List<XUser> targets = new ArrayList<>();
        for (User source : sources) {
            targets.add(toTarget(source));
        }
        return targets;
    }

    public User toSource(XUser target) {
        if (target == null) {
            return null;
        }
        User user = new User();
        user.setId(target.getId());
        user.setUsername(target.getUsername());
        user.setTitleBefore(target.getTitleBefore());
        user.setFirstname(target.getFirstName());
        user.setLastname(target.getLastName());
        user.setTitleAfter(target.getTitleAfter());
        user.setBirthday(target.getBirthDate());
        user.setAddress(new Address());// TODO
        user.setPhoneNumbers(target.getPhoneNumbers());
        user.setLoginEmail(target.getEmail());
        user.setEmails(asList(target.getEmail()));
        user.setProfileImagePath(target.getProfileImagePath());
        return user;

    }

    public List<User> toSources(List<XUser> targets) {
        if (targets == null) {
            return null;
        }
        List<User> sources = new ArrayList<>();
        for (XUser target : targets) {
            sources.add(toSource(target));
        }
        return sources;
    }

}
