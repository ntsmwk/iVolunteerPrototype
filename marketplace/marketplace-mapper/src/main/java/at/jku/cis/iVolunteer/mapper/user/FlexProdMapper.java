package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.FlexProd;
import at.jku.cis.iVolunteer.model.user.dto.FlexProdDTO;

@Mapper
public abstract class FlexProdMapper implements AbstractMapper<FlexProd, FlexProdDTO> {

}
