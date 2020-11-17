package at.jku.cis.iVolunteer.core.diagram;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.DiagramType;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.ValueType;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataPoint;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyEntry;

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
        List<XDiagramRawDataPoint> datapoints = dataset.getDatapoints();

        HashMap<List<String>, Float> occurrences = new HashMap<>();
        HashMap<Integer, Set<String>> uniqueValuesPerLevel = new HashMap<>();

        datapoints.forEach(dp -> {
            TreePropertyEntry treeProperty = dp.getTreeProperty();
            List<TreePropertyEntry> parents = treeProperty.getParents();

            parents.sort(Comparator.comparing(TreePropertyEntry::getLevel));

            List<String> data = new ArrayList<>();
            if (display.getDiagramType().equals(DiagramType.DOMAIN_CATEGORY)) {
                data.add(dp.getBereich());
            }

            // add other levels
            parents.forEach(p -> {
                data.add(p.getValue());
                uniqueValuesPerLevel.computeIfAbsent(p.getLevel(), k -> new HashSet<>()).add(p.getValue());
            });

            // add level 0
            data.add(treeProperty.getValue());
            uniqueValuesPerLevel.computeIfAbsent(treeProperty.getLevel(), k -> new HashSet<>())
                    .add(treeProperty.getValue());

            if (display.getValueType().equals(ValueType.COUNT)) {
                occurrences.merge(data, (float) 1, Float::sum);
            } else {
                // valueType == DURATION
                occurrences.merge(data, dp.getDuration(), Float::sum);
            }
        });

        System.out.println(occurrences);

    }

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
