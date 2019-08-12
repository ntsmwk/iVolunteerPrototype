package at.jku.cis.iVolunteer.marketplace.configurable.asset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.configurable.asset.ConfigurableAsset;

@Repository
public interface ConfigurableAssetRepository extends MongoRepository<ConfigurableAsset, String> {

}
