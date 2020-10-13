package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.Address;
import at.jku.cis.iVolunteer.model.user.XAddress;
import at.jku.cis.iVolunteer.model.user.XUserPassword;

@Component
public class XUserPasswordMapper implements AbstractMapper<CoreUser, XUserPassword> {

	@Override
	public XUserPassword toTarget(CoreUser source) {
		if (source == null) {
			return null;
		}

		XUserPassword user = new XUserPassword();
		user.setId(source.getId());
		user.setUsername(source.getUsername());
		user.setPassword(source.getPassword());
		user.setTitleBefore(source.getTitleBefore());
		user.setFirstName(source.getFirstname());
		user.setLastName(source.getLastname());
		user.setTitleAfter(source.getTitleAfter());
		user.setBirthDate(source.getBirthday());
		user.setAddress(new XAddress()); // TODO
		user.setPhoneNumbers(source.getPhoneNumbers());
		user.setEmail(source.getLoginEmail());
		user.setProfileImagePath(source.getProfileImagePath());

		return user;
	}

	@Override
	public List<XUserPassword> toTargets(List<CoreUser> sources) {
		if (sources == null) {
			return null;
		}
		List<XUserPassword> targets = new ArrayList<>();
		for (CoreUser source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}

	@Override
	public CoreUser toSource(XUserPassword target) {
		if (target == null) {
			return null;
		}

		CoreUser user = new CoreUser();
		user.setId(target.getId());
		user.setUsername(target.getUsername());
		user.setPassword(target.getPassword());
		user.setTitleBefore(target.getTitleBefore());
		user.setFirstname(target.getFirstName());
		user.setLastname(target.getLastName());
		user.setTitleAfter(target.getTitleAfter());
		user.setBirthday(target.getBirthDate());
		user.setAddress(new Address()); // TODO
		user.setPhoneNumbers(target.getPhoneNumbers());
		user.setLoginEmail(target.getEmail());
		user.setEmails(asList(target.getEmail()));
		user.setProfileImagePath(target.getProfileImagePath());
		return user;

	}

	@Override
	public List<CoreUser> toSources(List<XUserPassword> targets) {
		if (targets == null) {
			return null;
		}
		List<CoreUser> sources = new ArrayList<>();
		for (XUserPassword target : targets) {
			sources.add(toSource(target));
		}
		return sources;
	}

}
