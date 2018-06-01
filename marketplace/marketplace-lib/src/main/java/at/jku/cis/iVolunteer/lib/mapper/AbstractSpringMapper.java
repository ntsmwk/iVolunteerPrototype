package at.jku.cis.iVolunteer.lib.mapper;

import java.util.List;

import org.springframework.data.domain.Slice;

public abstract class AbstractSpringMapper<ENTITY, DTO> extends AbstractMapper<ENTITY, DTO> {

	protected AbstractSpringMapper(Class<ENTITY> enitityClazz, Class<DTO> dtoClazz) {
		super(enitityClazz, dtoClazz);
	}

	public List<DTO> toDTOs(Slice<ENTITY> entities) {
		return entities.map(p -> toDTO(p)).getContent();
	}

	public List<ENTITY> toEntities(Slice<DTO> dtos) {
		return dtos.map(p -> toEntity(p)).getContent();
	}

}