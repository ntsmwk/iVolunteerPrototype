package at.jku.cis.iVolunteer.marketplace.diagram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.diagram.StoredChart;

@Service
public class StoredChartService {

	@Autowired
	private StoredChartRepository storedChartRepository;

	public void createChart(StoredChart storedChart) {
		StoredChart chart = storedChartRepository.findByTitle(storedChart.getTitle());
		if (chart != null) {
			chart.setData(storedChart.getData());
			storedChartRepository.save(chart);
		} else {
			storedChartRepository.save(storedChart);
		}
	}

	public StoredChart getChart(String id) {
		return storedChartRepository.findOne(id);
	}

	public StoredChart getChartByTitle(String title) {
		return storedChartRepository.findByTitle(title);
	}

	public List<StoredChart> getCharts() {
		List<StoredChart> findAll = storedChartRepository.findAll();
		return findAll;
	}

}
