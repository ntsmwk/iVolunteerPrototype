package at.jku.cis.iVolunteer.lib.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

public abstract class AbstractMapper<ENTITY, DTO> {

	private final Class<ENTITY> enitityClazz;
	private final Class<DTO> dtoClazz;

	protected AbstractMapper(Class<ENTITY> enitityClazz, Class<DTO> dtoClazz) {
		super();
		this.enitityClazz = enitityClazz;
		this.dtoClazz = dtoClazz;
	}

	public List<ENTITY> toEntities(List<DTO> dtos) {
		return dtos.stream().map(p -> toEntity(p)).collect(Collectors.toList());
	}

	public List<DTO> toDTOs(List<ENTITY> entities) {
		return entities.stream().map(p -> toDTO(p)).collect(Collectors.toList());
	}

	public DTO toDTO(ENTITY entity, String... ignoreProperties) {
		if (entity == null)
			return null;
		try {
			final DTO dto = dtoClazz.newInstance();
			BeanUtils.copyProperties(entity, dto, ignoreProperties);
			return dto;
		} catch (InstantiationException | IllegalAccessException e) {
		}
		return null;
	}

	public ENTITY toEntity(DTO dto, String... ignoreProperties) {
		if (dto == null)
			return null;
		try {
			final ENTITY entity = enitityClazz.newInstance();
			BeanUtils.copyProperties(dto, entity, ignoreProperties);
			return entity;
		} catch (InstantiationException | IllegalAccessException e) {
		}
		return null;
	}

}
