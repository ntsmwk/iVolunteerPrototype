package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.Address;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.XAddress;
import at.jku.cis.iVolunteer.model.user.XUser;
import at.jku.cis.iVolunteer.model.user.XUserPassword;

public class XUserMapper implements AbstractMapper<User, XUser> {

	@Override
	public XUser toTarget(User source) {
		if (source == null) {
			return null;
		}

		XUserPassword user = new XUserPassword();
		user.setId(source.getId());
		user.setUsername(source.getUsername());
		user.setTitleBefore(source.getTitleBefore());
		user.setFirstname(source.getFirstname());
		user.setLastname(source.getLastname());
		user.setTitleAfter(source.getTitleAfter());
		user.setBirthDate(source.getBirthday());
		user.setAddress(new XAddress());// TODO
		user.setPhoneNumbers(source.getPhoneNumbers());
		user.setEmails(source.getEmails());
		user.setProfileImagePath(source.getProfileImagePath());

		return user;
	}

	@Override
	public List<XUser> toTargets(List<User> sources) {
		if (sources == null) {
			return null;
		}
		List<XUser> targets = new ArrayList<>();
		for (User source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}

	@Override
	public User toSource(XUser target) {
		if (target == null) {
			return null;
		}
		User user = new User();
		user.setId(target.getId());
		user.setUsername(target.getUsername());
		user.setTitleBefore(target.getTitleBefore());
		user.setFirstname(target.getFirstname());
		user.setLastname(target.getLastname());
		user.setTitleAfter(target.getTitleAfter());
		user.setBirthday(target.getBirthDate());
		user.setAddress(new Address());// TODO
		user.setPhoneNumbers(target.getPhoneNumbers());
		user.setEmails(target.getEmails());
		user.setProfileImagePath(target.getProfileImagePath());
		return user;

	}

	@Override
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
