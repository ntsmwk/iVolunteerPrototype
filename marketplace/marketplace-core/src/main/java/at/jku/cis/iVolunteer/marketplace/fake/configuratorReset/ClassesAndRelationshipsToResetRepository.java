package at.jku.cis.iVolunteer.marketplace.fake.configuratorReset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.fake.configuratorReset.ClassesAndRelationshipsToReset;


@Repository
public interface ClassesAndRelationshipsToResetRepository extends MongoRepository<ClassesAndRelationshipsToReset, String>{


}
