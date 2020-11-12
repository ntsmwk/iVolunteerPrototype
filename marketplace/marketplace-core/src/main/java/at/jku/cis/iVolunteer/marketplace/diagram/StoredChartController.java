package at.jku.cis.iVolunteer.marketplace.diagram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.diagram.StoredChart;

@RestController
@RequestMapping("/chart")
public class StoredChartController {

	@Autowired
	private StoredChartService storedChartService;

	@GetMapping
	public List<StoredChart> getCharts() {
		return storedChartService.getCharts();
	}

	@GetMapping("/id/{chartId}")
	public StoredChart getChart(@PathVariable String chartId) {
		return storedChartService.getChart(chartId);
	}

	@GetMapping("/title/{chartTitle}")
	public StoredChart getChartByTitle(@PathVariable String chartTitle) {
		return storedChartService.getChartByTitle(chartTitle);
	}

	@PostMapping
	public void createChart(@RequestBody StoredChart storedChart) {
		storedChartService.createChart(storedChart);
	}

}
