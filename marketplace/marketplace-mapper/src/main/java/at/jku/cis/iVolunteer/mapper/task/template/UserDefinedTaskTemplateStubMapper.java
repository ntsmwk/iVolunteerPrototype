package at.jku.cis.iVolunteer.mapper.task.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateStubDTO;

@Component
public class UserDefinedTaskTemplateStubMapper implements AbstractMapper<UserDefinedTaskTemplate, UserDefinedTaskTemplateStubDTO> {

    @Override
    public UserDefinedTaskTemplateStubDTO toDTO(UserDefinedTaskTemplate source) {
        if ( source == null ) {
            return null;
        }

        UserDefinedTaskTemplateStubDTO userDefinedTaskTemplateStubDTO = new UserDefinedTaskTemplateStubDTO();

        userDefinedTaskTemplateStubDTO.setId( source.getId() );
        userDefinedTaskTemplateStubDTO.setName( source.getName() );
        userDefinedTaskTemplateStubDTO.setDescription( source.getDescription() );
        
        if (source instanceof SingleUserDefinedTaskTemplate) {
        	userDefinedTaskTemplateStubDTO.setKind("single");
        } else if (source instanceof MultiUserDefinedTaskTemplate) {
        	userDefinedTaskTemplateStubDTO.setKind("multi");
        } else {
        	throw new IllegalStateException("source has to be either Single- or Nested-UserDefinedTaskTemplate");
        }

        return userDefinedTaskTemplateStubDTO;
    }

    @Override
    public List<UserDefinedTaskTemplateStubDTO> toDTOs(List<UserDefinedTaskTemplate> sources) {
        if ( sources == null ) {
            return null;
        }

        List<UserDefinedTaskTemplateStubDTO> list = new ArrayList<UserDefinedTaskTemplateStubDTO>( sources.size() );
        for ( UserDefinedTaskTemplate userDefinedTaskTemplate : sources ) {
            list.add( toDTO( userDefinedTaskTemplate ) );
        }

        return list;
    }

    @Override
    public UserDefinedTaskTemplate toEntity(UserDefinedTaskTemplateStubDTO target) {
        if ( target == null ) {
            return null;
        }

        UserDefinedTaskTemplate userDefinedTaskTemplate = new UserDefinedTaskTemplate();

        userDefinedTaskTemplate.setId( target.getId() );
        userDefinedTaskTemplate.setName( target.getName() );
        userDefinedTaskTemplate.setDescription( target.getDescription() );

        return userDefinedTaskTemplate;
    }

    @Override
    public List<UserDefinedTaskTemplate> toEntities(List<UserDefinedTaskTemplateStubDTO> targets) {
        if ( targets == null ) {
            return null;
        }

        List<UserDefinedTaskTemplate> list = new ArrayList<UserDefinedTaskTemplate>( targets.size() );
        for ( UserDefinedTaskTemplateStubDTO userDefinedTaskTemplateStubDTO : targets ) {
            list.add( toEntity( userDefinedTaskTemplateStubDTO ) );
        }

        return list;
    }
}