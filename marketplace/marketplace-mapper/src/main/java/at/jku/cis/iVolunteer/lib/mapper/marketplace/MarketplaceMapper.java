package at.jku.cis.iVolunteer.lib.mapper.marketplace;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.marketplace.dto.MarketplaceDTO;

@Mapper
public abstract class MarketplaceMapper implements AbstractMapper<Marketplace, MarketplaceDTO> {

}
