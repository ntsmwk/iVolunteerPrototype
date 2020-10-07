package at.jku.cis.iVolunteer.marketplace.user.classdefinition;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Repository
public interface UserClassInstanceRepository extends MongoRepository<ClassInstance, String> {


}