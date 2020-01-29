package at.jku.cis.iVolunteer.marketplace.fake;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IsSunburstFakeRepository extends MongoRepository<IsSunburstFakeDocument, String>{


}
