package at.jku.cis.iVolunteer.model._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;
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
		ts.setAbbreviation(source.getAbbreviation());
		ts.setDescription(source.getDescription());
		ts.setHomepage(source.getHomepage());
		ts.setImagePath(source.getImagePath());
		ts.setPrimaryColor(source.getPrimaryColor());
		ts.setSecondaryColor(source.getSecondaryColor());
		ts.setTags(source.getTags());
		ts.setGeoInfo(new XGeoInfo(source.getLocation()));

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
