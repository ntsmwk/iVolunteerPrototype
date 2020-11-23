package at.jku.cis.iVolunteer.configurator.model._mapper;

import java.util.List;

public interface AbstractMapper<S, T> {

	T toTarget(S source);

	List<T> toTargets(List<S> sources);

	S toSource(T target);

	List<S> toSources(List<T> targets);
}
