package at.jku.cis.iVolunteer.core.diagram;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.DiagramType;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.ValueType;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramData;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramDataArray;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramDataPoint;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramReturnEntity;
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

        if (datasets.size() == 0) {
            return new XDiagramRawDataSet();
        }

        return datasets.get(0);
    }

    public XDiagramRawDataSet filter(XDiagramRawDataSet dataset, XDiagramFilter filter) {
        if (filter.getStart() == null) {
            LocalDateTime d = LocalDateTime.of(1900, 1, 1, 0, 0);
            Date date = Date.from(d.atZone(ZoneId.systemDefault()).toInstant());

            filter.setStart(date);
        }

        if (filter.getEnd() == null) {
            LocalDateTime d = LocalDateTime.now();
            d = d.plusDays(1);
            Date date = Date.from(d.atZone(ZoneId.systemDefault()).toInstant());

            filter.setEnd(date);
        }

        if (filter.getTenantIds() == null || filter.getTenantIds().size() == 0) {
            dataset.getDatapoints().stream()
                    .filter(d -> d.getStartDate().after(filter.getStart()) && d.getEndDate().before(filter.getEnd()))
                    .collect(Collectors.toList());
        } else {
            dataset.getDatapoints().stream().filter(d -> d.getStartDate().after(filter.getStart())
                    && d.getEndDate().before(filter.getEnd()) && filter.getTenantIds().contains(d.getTenantId()))
                    .collect(Collectors.toList());
        }

        return dataset;
    }

    public XDiagramReturnEntity generateSunburstData(XDiagramRawDataSet dataset, XDiagramDisplay display) {
        List<XDiagramRawDataPoint> datapoints = dataset.getDatapoints();

        HashMap<List<String>, Float> occurrences = new HashMap<>();
        // example: <(Einsatz,Technisch,T1), 50>

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
            });

            // add level 0
            data.add(treeProperty.getValue());

            if (display.getValueType().equals(ValueType.COUNT)) {
                occurrences.merge(data, (float) 1, Float::sum);
            } else {
                // valueType == DURATION
                occurrences.merge(data, dp.getDuration(), Float::sum);
            }
        });

        List<XDiagramData> diagramData = new ArrayList<>();
        occurrences.forEach((values, duration) -> {
            insertValues(diagramData, 0, values.size() - 1, values, duration);
        });

        return new XDiagramReturnEntity(new Date(), diagramData);

    }

    private List<XDiagramData> insertValues(List<XDiagramData> data, int depth, int maxDepth, List<String> values,
            float duration) {

        if (depth == maxDepth) {
            data.add(new XDiagramDataPoint(values.get(depth), duration));
        } else {
            String currentVal = values.get(depth);
            if (data.stream().map(d -> d.getName()).anyMatch(name -> name.equals(currentVal))) {
            } else {
                data.add(new XDiagramDataArray(values.get(depth)));
            }
            XDiagramDataArray currentData = (XDiagramDataArray) data.stream()
                    .filter(d -> d.getName().equals(currentVal)).findFirst().orElse(null);
            insertValues(currentData.getChildren(), depth + 1, maxDepth, values, duration);
        }
        return data;
    }

    @Scheduled(fixedDelay = 1_800_000, initialDelay = 60_000) // 30min, 1min
    private void queryDiagramRawDataSetsFromMarketplaces() {
        // TODO
        // currently each 30min all datasets are added to db
        // method above returns only latest datasets
        // -> remove older ones automatically from db

        List<XDiagramRawDataSet> datasets = diagramDataRestClient.getDiagramRawData();
        diagramRawDataSetRepository.save(datasets);
    }

    public void queryDiagramRawDataSetsFromMarketplacesByUser(String userId) {
        List<XDiagramRawDataSet> datasets = diagramDataRestClient.getDiagramRawDataByUser(userId);
        diagramRawDataSetRepository.save(datasets);

    }

}
