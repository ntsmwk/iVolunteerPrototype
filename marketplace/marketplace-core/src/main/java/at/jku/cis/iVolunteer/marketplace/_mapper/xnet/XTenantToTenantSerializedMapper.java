package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.XDynamicField;
import at.jku.cis.iVolunteer.model.task.XTaskSerialized;
import at.jku.cis.iVolunteer.model.user.XColor;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

@Component
public class XTenantToTenantSerializedMapper implements OneWayMapper<Tenant, XTenantSerialized> {

	@Override
	public XTenantSerialized toTarget(Tenant source) {
		if (source == null) {
			return null;
		}

		XTenantSerialized ts = new XTenantSerialized();

		ts.setId(source.getId());
		ts.setName(source.getName());
		ts.setAbbreviation(null);
		ts.setDescription(source.getDescription());
		ts.setHomepage(source.getHomepage());
		ts.setImagePath(source.getImagePath());
		ts.setPrimaryColor(new XColor(source.getPrimaryColor()));
		ts.setSecondaryColor(new XColor(source.getSecondaryColor()));
		ts.setTags(source.getTags());
		ts.setGeoInfo(null);

		return ts;

	}

	@Override
	public List<XTenantSerialized> toTargets(List<Tenant> sources) {
		if (sources == null) {
			return null;
		}
		List<XTenantSerialized> targets = new ArrayList<>();
		for (Tenant source : sources) {
			targets.add(toTarget(source));
		}
		return targets;
	}

}
