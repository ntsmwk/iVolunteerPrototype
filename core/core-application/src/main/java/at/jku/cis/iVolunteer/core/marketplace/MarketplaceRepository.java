package at.jku.cis.iVolunteer.core.marketplace;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

public interface MarketplaceRepository extends MongoRepository<Marketplace, String> {

	Marketplace findByName(String name);
	Marketplace findByUrl(String url);
}
