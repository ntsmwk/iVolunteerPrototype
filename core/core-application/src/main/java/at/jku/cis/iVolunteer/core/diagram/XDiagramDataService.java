package at.jku.cis.iVolunteer.core.diagram;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataPoint;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;

@Service
public class XDiagramDataService {
    @Autowired
    XDiagramRawDataSetRepository diagramRawDataSetRepository;
    @Autowired
    XDiagramDataRestClient diagramDataRestClient;

    public XDiagramRawDataSet getLatestDiagramData(CoreUser user) {
        List<XDiagramRawDataSet> datasets = diagramRawDataSetRepository.findByUserId(user.getId());
        datasets.sort(Comparator.comparing(XDiagramRawDataSet::getRefreshTimestamp).reversed());

        return datasets.get(0);
    }

    public XDiagramRawDataSet filter(XDiagramRawDataSet dataset, XDiagramFilter filter) {
        // TODO: deal with null values
        List<XDiagramRawDataPoint> datapoints = dataset.getDatapoints();
        datapoints = datapoints
                .stream().filter(d -> d.getStartDate().after(filter.getStart())
                        && d.getEndDate().before(filter.getEnd()) && filter.getTenantIds().contains(d.getTenantId()))
                .collect(Collectors.toList());

        dataset.setDatapoints(datapoints);
        return dataset;
    }

    public void calcDiagramData(XDiagramRawDataSet dataset, XDiagramDisplay display) {
        dataset.getDatapoints();

        display.getDiagramType(); // DOMAIN_CATEGORY / CATEGORY_ONLY
        display.getValueType(); // number / duration

        // calc diagram data
        // 

    }

    // ---------

    @Scheduled(fixedDelay = 1_800_000, initialDelay = 60_000) // 30min, 1min
    private void queryDiagramDataSetsFromMArketplaces() {
        // TODO
        // currently each 30min all datasets are added to db
        // method above returns only latest datasets
        // -> remove older ones automatically from db

        List<XDiagramRawDataSet> datasets = diagramDataRestClient.getDiagramData();
        diagramRawDataSetRepository.save(datasets);
    }

}
