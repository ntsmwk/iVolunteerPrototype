package at.jku.cis.iVolunteer.marketplace.diagram.xnet;

import java.time.Instant;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.badge.XBadgeCertificateRepository;
import at.jku.cis.iVolunteer.marketplace.commons.DateTimeService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataPoint;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyEntry;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class XDiagramDataService {
    @Autowired
    private DateTimeService dateTimeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassInstanceRepository classInstanceRepository;
    @Autowired
    private XBadgeCertificateRepository badgeCertificateRepository;

    public List<XDiagramRawDataSet> calcRawDataSets() {
        List<XDiagramRawDataSet> datasets = new ArrayList<>();

        List<User> users = userService.getUsersByRole(UserRole.VOLUNTEER);
        users.forEach(user -> {
            XDiagramRawDataSet dataset = calcRawDataSetPerUser(user.getId());
            if (dataset != null) {
                datasets.add(dataset);
            }
        });

        return datasets;
    }

    public XDiagramRawDataSet calcRawDataSetPerUser(String userId) {
        List<XDiagramRawDataPoint> datapoints = new ArrayList<>();

        List<ClassInstance> classInstances = classInstanceRepository.getByUserId(userId);
        if (classInstances.size() > 0) {
            classInstances.stream().map(ci -> toRawDataPoint(ci)).forEach(datapoints::add);

            XDiagramRawDataSet dataset = new XDiagramRawDataSet();
            dataset.setDatapoints(datapoints);
            dataset.setUserId(userId);
            dataset.setRefreshTimestamp(new Date());
            dataset.setBadges(badgeCertificateRepository.findAllByUserId(userId));

            return dataset;
        }
        return null;
    }

    private XDiagramRawDataPoint toRawDataPoint(ClassInstance ci) {
        // TODO Philipp: if necessary, add more properties to rawData

        XDiagramRawDataPoint datapoint = new XDiagramRawDataPoint();
        datapoint.setTenantId(ci.getTenantId());

        PropertyInstance<Object> startingDate = ci.findProperty("Starting Date");
        if (startingDate != null && startingDate.getValues().size() > 0) {
            try {
                datapoint.setStartDate(((Date) startingDate.getValues().get(0)));
            } catch (ClassCastException e) {
                try {
                    Date parsedDate = dateTimeService
                            .parseMultipleDateFormats((String) startingDate.getValues().get(0));
                    datapoint.setStartDate(parsedDate);
                } catch (Exception f) {
                    Instant instant = Instant.ofEpochMilli((Long) startingDate.getValues().get(0));
                    Date d = Date.from(instant);
                    datapoint.setStartDate(d);
                }
            }
        }

        PropertyInstance<Object> endDate = ci.findProperty("End Date");
        if (endDate != null && endDate.getValues().size() > 0) {
            try {
                datapoint.setEndDate(((Date) endDate.getValues().get(0)));
            } catch (ClassCastException e) {
                try {
                    Date parsedDate = dateTimeService.parseMultipleDateFormats((String) endDate.getValues().get(0));
                    datapoint.setEndDate(parsedDate);
                } catch (Exception f) {
                    Instant instant = Instant.ofEpochMilli((Long) endDate.getValues().get(0));
                    Date d = Date.from(instant);
                    datapoint.setEndDate(d);
                }
            }
        }

        PropertyInstance<Object> duration = ci.findProperty("Duration");
        if (duration != null && duration.getValues().size() > 0) {
            datapoint.setDuration(objectMapper.convertValue(duration.getValues().get(0), Float.class));
        }

        PropertyInstance<Object> bereich = ci.findProperty("Bereich");
        if (bereich != null && bereich.getValues().size() > 0) {
            datapoint.setBereich((String) bereich.getValues().get(0));
        }

        PropertyInstance<Object> taskTypeField = ci.getProperties().stream()
                .filter(p -> p.getName().equals("TaskType") && PropertyType.TREE.equals(p.getType())).findFirst()
                .orElse(null);

        if (taskTypeField == null) {
            taskTypeField = ci.getProperties().stream().filter(p -> PropertyType.TREE.equals(p.getType())).findFirst()
                    .orElse(null);
        }
        if (taskTypeField != null) {
            if (taskTypeField.getValues().size() > 0) {
                try {
                    datapoint.setTreeProperty(
                            objectMapper.convertValue(taskTypeField.getValues().get(0), TreePropertyEntry.class));
                } catch (Exception e) {

                }
            }
        }

        return datapoint;
    }
}
