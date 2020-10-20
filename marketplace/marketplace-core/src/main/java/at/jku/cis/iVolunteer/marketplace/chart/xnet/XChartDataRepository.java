package at.jku.cis.iVolunteer.marketplace.chart.xnet;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartData;

public interface XChartDataRepository extends MongoRepository<XChartData, String> {

    XChartData findById(String id);

    List<XChartData> findByUserId(String userId);

}
