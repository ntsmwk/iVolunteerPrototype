package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.FlexProd;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.dto.FlexProdDTO;
import at.jku.cis.iVolunteer.model.user.dto.HelpSeekerDTO;

@Mapper
public abstract class FlexProdMapper implements AbstractMapper<FlexProd, FlexProdDTO> {

}
