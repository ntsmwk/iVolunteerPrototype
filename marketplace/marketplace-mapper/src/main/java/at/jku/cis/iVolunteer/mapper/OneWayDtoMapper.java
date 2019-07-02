package at.jku.cis.iVolunteer.mapper;

import java.util.List;

public interface OneWayDtoMapper<S, T> {

	T toDTO(S source);

	List<T> toDTOs(List<S> sources);


}
