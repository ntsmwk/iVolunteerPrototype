package at.jku.cis.iVolunteer.core.chart;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;

public interface XChartDataSetRepository extends MongoRepository<XChartDataSet, String> {

    XChartDataSet findById(String id);

    List<XChartDataSet> findByUserId(String userId);

}
