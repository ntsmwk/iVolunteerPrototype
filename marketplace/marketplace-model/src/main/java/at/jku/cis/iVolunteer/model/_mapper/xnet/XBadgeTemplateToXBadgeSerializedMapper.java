package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.badge.XBadgeSerialized;
import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;

@Component
public class XBadgeTemplateToXBadgeSerializedMapper implements OneWayMapper<XBadgeTemplate, XBadgeSerialized> {

	@Override
	public XBadgeSerialized toTarget(XBadgeTemplate source) {
		XBadgeSerialized badgeSerialized = new XBadgeSerialized();
		badgeSerialized.setId(ObjectId.get().toHexString());
		badgeSerialized.setName(source.getName());
		badgeSerialized.setDescription(source.getDescription());
		badgeSerialized.setImagePath(source.getImagePath());
		return badgeSerialized;
	}

	@Override
	public List<XBadgeSerialized> toTargets(List<XBadgeTemplate> sources) {
		return sources.stream().map(s -> this.toTarget(s)).collect(Collectors.toList());
	}

}
