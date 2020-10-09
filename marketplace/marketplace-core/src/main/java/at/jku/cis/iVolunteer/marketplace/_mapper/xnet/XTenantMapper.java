package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.marketplace._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
import at.jku.cis.iVolunteer.model.user.XColor;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

public class XTenantMapper implements AbstractMapper<Tenant, XTenant> {

	@Override
	public XTenant toTarget(Tenant source) {
		if (source == null) {return null;}
		XTenant xt = new XTenant();	
		xt.setId(source.getId());
		xt.setName(source.getName());
		xt.setAbbreviation("todo"); //TODO
		xt.setDescription(source.getDescription());
		xt.setHomepage(source.getHomepage());
		xt.setImagePath(source.getImagePath());
		xt.setPrimaryColor(new XColor(source.getPrimaryColor())); //TODO
		xt.setSecondaryColor(new XColor(source.getSecondaryColor())); //TODO
		xt.setMarketplaceURL(source.getMarketplaceId());
		xt.setTags(source.getTags());
		xt.setLandingpageMessage(source.getLandingpageMessage());
		xt.setLandingpageTitle(source.getLandingpageTitle());
		xt.setLandingpageText(source.getLandingpageText());
		xt.setLandingpageImagePath(source.getLandingpageImagePath());
		xt.setGeoInfo(new XGeoInfo()); //TODO
		xt.setSubscribedVolunteers(new ArrayList<>()); //TODO
		
		return xt;
	}

	@Override
	public List<XTenant> toTargets(List<Tenant> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tenant toSource(XTenant target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tenant> toSources(List<XTenant> targets) {
		// TODO Auto-generated method stub
		return null;
	}

}
