package at.jku.cis.iVolunteer.marketplace._mapper.z_etc;
//package at.jku.cis.iVolunteer.mapper.task.template;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.mapper.AbstractMapper;
//import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
//import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
//import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
//import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplateStub;
//
//@Component
//public class UserDefinedTaskTemplateStubMapper
//		implements AbstractMapper<UserDefinedTaskTemplate, UserDefinedTaskTemplateStub> {
//
//	@Override
//	public UserDefinedTaskTemplateStub toTarget(UserDefinedTaskTemplate source) {
//		if (source == null) {
//			return null;
//		}
//
//		UserDefinedTaskTemplateStub userDefinedTaskTemplateStubDTO = new UserDefinedTaskTemplateStub();
//
//		userDefinedTaskTemplateStubDTO.setId(source.getId());
//		userDefinedTaskTemplateStubDTO.setName(source.getName());
//		userDefinedTaskTemplateStubDTO.setDescription(source.getDescription());
//
//		if (source instanceof SingleUserDefinedTaskTemplate) {
//			userDefinedTaskTemplateStubDTO.setKind("single");
//		} else if (source instanceof MultiUserDefinedTaskTemplate) {
//			userDefinedTaskTemplateStubDTO.setKind("multi");
//		} else {
//			throw new IllegalStateException("source has to be either Single- or Nested-UserDefinedTaskTemplate");
//		}
//
//		return userDefinedTaskTemplateStubDTO;
//	}
//
//	@Override
//	public List<UserDefinedTaskTemplateStub> toTargets(List<UserDefinedTaskTemplate> sources) {
//		if (sources == null) {
//			return null;
//		}
//
//		List<UserDefinedTaskTemplateStub> list = new ArrayList<UserDefinedTaskTemplateStub>(sources.size());
//		for (UserDefinedTaskTemplate userDefinedTaskTemplate : sources) {
//			list.add(toTarget(userDefinedTaskTemplate));
//		}
//
//		return list;
//	}
//
//	@Override
//	public UserDefinedTaskTemplate toSource(UserDefinedTaskTemplateStub target) {
//		if (target == null) {
//			return null;
//		}
//
//		UserDefinedTaskTemplate userDefinedTaskTemplate = new UserDefinedTaskTemplate();
//
//		userDefinedTaskTemplate.setId(target.getId());
//		userDefinedTaskTemplate.setName(target.getName());
//		userDefinedTaskTemplate.setDescription(target.getDescription());
//
//		return userDefinedTaskTemplate;
//	}
//
//	@Override
//	public List<UserDefinedTaskTemplate> toSources(List<UserDefinedTaskTemplateStub> targets) {
//		if (targets == null) {
//			return null;
//		}
//
//		List<UserDefinedTaskTemplate> list = new ArrayList<UserDefinedTaskTemplate>(targets.size());
//		for (UserDefinedTaskTemplateStub userDefinedTaskTemplateStubDTO : targets) {
//			list.add(toSource(userDefinedTaskTemplateStubDTO));
//		}
//
//		return list;
//	}
//}