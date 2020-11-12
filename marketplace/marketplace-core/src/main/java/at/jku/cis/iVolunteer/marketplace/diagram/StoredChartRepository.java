package at.jku.cis.iVolunteer.marketplace.diagram;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.diagram.StoredChart;

@Repository
public interface StoredChartRepository extends MongoRepository<StoredChart, String> {

	StoredChart findByTitle(String title);
}
