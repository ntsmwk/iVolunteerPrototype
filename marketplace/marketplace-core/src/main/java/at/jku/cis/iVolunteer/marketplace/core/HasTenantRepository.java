package at.jku.cis.iVolunteer.marketplace.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

public interface HasTenantRepository<T extends IVolunteerObject, S extends Serializable> extends MongoRepository<T, S> {

	List<T> findByTenantId(String tenantId);

}
