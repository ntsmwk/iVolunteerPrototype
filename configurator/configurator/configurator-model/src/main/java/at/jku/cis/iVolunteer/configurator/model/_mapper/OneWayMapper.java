package at.jku.cis.iVolunteer.configurator.model._mapper;

import java.util.List;

public interface OneWayMapper<S, T> {
	T toTarget(S source);

	List<T> toTargets(List<S> sources);
}
