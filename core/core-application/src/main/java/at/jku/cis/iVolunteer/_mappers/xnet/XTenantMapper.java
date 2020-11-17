package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.model._httpresponses.XTenantSubscribedResponse;
import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model._mapper.xnet.XUserMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.tenant.XTenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.XColor;
import at.jku.cis.iVolunteer.model.user.XGeoInfo;

@Component
public class XTenantMapper {

	@Autowired MarketplaceService marketplaceService;
	@Autowired XUserMapper xUserMapper;

	public XTenant toTarget(Tenant source, List<CoreUser> users) {

		if (source == null) {
			return null;
		}
		XTenant xt = new XTenant();
		xt.setId(source.getId());
		xt.setName(source.getName());
		xt.setAbbreviation(source.getAbbreviation());
		xt.setDescription(source.getDescription());
		xt.setHomepage(source.getHomepage());
		xt.setImagePath(source.getImagePath());
		xt.setPrimaryColor(source.getPrimaryColor());
		xt.setSecondaryColor(source.getSecondaryColor());

		Marketplace mp = marketplaceService.findById(source.getMarketplaceId());
		if (mp != null) {
			xt.setMarketplaceUrl(mp.getUrl());
		}
		xt.setTags(source.getTags());
		xt.setLandingpageMessage(source.getLandingpageMessage());
		xt.setLandingpageTitle(source.getLandingpageTitle());
		xt.setLandingpageText(source.getLandingpageText());
		xt.setLandingpageImagePath(source.getLandingpageImagePath());
		xt.setSubscribedVolunteers(xUserMapper.toTargets(users));
		xt.setGeoInfo(new XGeoInfo(source.getLocation()));
		return xt;
	}

	public XTenantSubscribedResponse toTenantSubscribedResponse(Tenant source, List<CoreUser> users) {

		if (source == null) {
			return null;
		}
		XTenantSubscribedResponse xt = new XTenantSubscribedResponse();
		xt.setId(source.getId());
		xt.setName(source.getName());
		xt.setAbbreviation(source.getAbbreviation());
		xt.setDescription(source.getDescription());
		xt.setHomepage(source.getHomepage());
		xt.setImagePath(source.getImagePath());
		xt.setPrimaryColor(source.getPrimaryColor());
		xt.setSecondaryColor(source.getSecondaryColor());

		Marketplace mp = marketplaceService.findById(source.getMarketplaceId());
		if (mp != null) {
			xt.setMarketplaceUrl(mp.getUrl());
		}
		xt.setTags(source.getTags());
		xt.setLandingpageMessage(source.getLandingpageMessage());
		xt.setLandingpageTitle(source.getLandingpageTitle());
		xt.setLandingpageText(source.getLandingpageText());
		xt.setLandingpageImagePath(source.getLandingpageImagePath());
		xt.setSubscribedVolunteers(xUserMapper.toTargets(users));
		xt.setGeoInfo(new XGeoInfo(source.getLocation()));
		return xt;
	}

//	@Override
//	public List<XTenant> toTargets(List<Tenant> sources) {
//		if (sources == null) {return null; }
//		
//		List<XTenant> targets = new LinkedList<>();
//		for (Tenant source : sources) {
//			targets.add(toTarget(source));
//		}
//		return targets;
//	}

	public Tenant toSource(XTenant target) {
		if (target == null) {
			return null;
		}

		Tenant tenant = new Tenant();
		tenant.setId(target.getId());
		tenant.setName(target.getName());
		tenant.setAbbreviation(target.getAbbreviation()); // TODO
		tenant.setDescription(target.getDescription());
		tenant.setHomepage(target.getHomepage());
		tenant.setImagePath(target.getImagePath());
		tenant.setPrimaryColor(target.getPrimaryColor());
		tenant.setSecondaryColor(target.getSecondaryColor());

		
		Marketplace mp = marketplaceService.findByUrl(target.getMarketplaceUrl());
		if (mp != null) {
			tenant.setMarketplaceId(mp.getId());
		}

		tenant.setTags(target.getTags());
		tenant.setLandingpageMessage(target.getLandingpageMessage());
		tenant.setLandingpageTitle(target.getLandingpageTitle());
		tenant.setLandingpageText(target.getLandingpageText());
		tenant.setLandingpageImagePath(target.getLandingpageImagePath());
		tenant.setLocation(new Location(target.getGeoInfo()));

		return tenant;
	}

	public List<Tenant> toSources(List<XTenant> targets) {
		if (targets == null) {
			return null;
		}

		List<Tenant> sources = new ArrayList<>();
		for (XTenant target : targets) {
			sources.add(toSource(target));
		}
		return sources;
	}

}
