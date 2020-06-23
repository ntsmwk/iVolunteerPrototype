package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public interface Criteria {
	
   public List<ClassInstance> meetCriteria(List<ClassInstance> instances);
}
