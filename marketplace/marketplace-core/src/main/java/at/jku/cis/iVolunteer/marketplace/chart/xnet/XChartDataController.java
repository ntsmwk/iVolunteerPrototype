package at.jku.cis.iVolunteer.marketplace.chart.xnet;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;

@RestController
@RequestMapping("/chartdata")
public class XChartDataController<T extends Serializable> {
    @Autowired
    private XChartDataService chartDataService;

    @GetMapping()
    public List<XChartDataSet> getAllChartData() {
        return chartDataService.generateChartDataSets();
    }
}
