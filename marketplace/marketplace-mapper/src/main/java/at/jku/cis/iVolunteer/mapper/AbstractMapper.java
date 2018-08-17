package at.jku.cis.iVolunteer.mapper;

import java.util.List;

public interface AbstractMapper<S, T> {

	T toDTO(S source);

	List<T> toDTOs(List<S> sources);

	S toEntity(T target);

	List<S> toEntities(List<T> targets);

}
