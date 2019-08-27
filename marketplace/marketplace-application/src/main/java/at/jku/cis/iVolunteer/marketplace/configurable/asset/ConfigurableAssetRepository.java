package at.jku.cis.iVolunteer.marketplace.configurable.asset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;

@Repository
public interface ConfigurableAssetRepository extends MongoRepository<ClassInstance, String> {

}
