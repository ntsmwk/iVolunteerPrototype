package at.jku.cis.iVolunteer.core.chart;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Service
public class XChartDataService {
    @Autowired
    XChartDataSetRepository chartDataSetRepository;
    @Autowired
    XChartDataRestClient chartDataRestClient;

    public List<XChartDataSet> getLatestChartData(CoreUser user) {
        List<XChartDataSet> datasets = chartDataSetRepository.findByUserId(user.getId());
        datasets.sort(Comparator.comparing(XChartDataSet::getTimestamp).reversed());
        datasets = datasets.stream().distinct().collect(Collectors.toList());

        return datasets;
    }

    @Scheduled(fixedDelay = 1_800_000, initialDelay = 60_000) // 30min, 1min
    private void queryChartDataSetsFromMArketplaces() {
        // TODO
        // currently each 30min all datasets are just added
        // method above returns onle latest datasets
        // -> remove older ones automatically

        // TODO
        // call from core to mp without authentication
        // excepted in mp WebSecurityConfig
        List<XChartDataSet> datasets = chartDataRestClient.getChartDataFromMarketplaces();
        chartDataSetRepository.save(datasets);
    }

}
