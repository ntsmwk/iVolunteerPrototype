package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

public interface Criteria {

	public List<ClassInstance> meetCriteria(List<ClassInstance> instances);
}
