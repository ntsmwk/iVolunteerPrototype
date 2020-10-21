package at.jku.cis.iVolunteer.marketplace.chart.xnet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;
import at.jku.cis.iVolunteer.model.chart.xnet.XDataPoint;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class XChartDataService {
    @Autowired
    UserService userService;
    @Autowired
    ClassInstanceRepository classInstanceRepository;

    // public List<XChartDataSet> getChartData() {
    // return chartDataRepository.findAll();
    // }

    // public XChartDataSet getChartDataById(String id) {
    // return chartDataRepository.findById(id);
    // }

    // public List<XChartDataSet> getLatestChartData() {
    // List<User> users = userService.getUsersByRole(UserRole.VOLUNTEER);

    // List<XChartDataSet> datasets = new ArrayList<>();
    // users.forEach(user -> {
    // datasets.addAll(getLatestChartDataByUserId(user.getId()));
    // });

    // return datasets;
    // }

    // public List<XChartDataSet> getLatestChartDataByUserId(String userId) {
    // // return only latest data sets per user
    // // maybe remove old data sets from database too

    // List<XChartDataSet> datasets = chartDataRepository.findByUserId(userId);
    // datasets.sort(Comparator.comparing(XChartDataSet::getTimestamp).reversed());
    // datasets = datasets.stream().distinct().collect(Collectors.toList());

    // return datasets;
    // }

    // @Scheduled(fixedDelay = 180_0000) // 30min
    public List<XChartDataSet> generateChartDataSets() {
        List<XChartDataSet> datasets = new ArrayList<>();

        List<User> users = userService.getUsersByRole(UserRole.VOLUNTEER);
        users.forEach(user -> {
            List<String> tenantIds = user.getSubscribedTenants().stream()
                    .filter(s -> s.getRole().equals(UserRole.VOLUNTEER)).map(s -> s.getTenantId()).distinct()
                    .collect(Collectors.toList());

            tenantIds.forEach(tenantId -> {
                List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(user.getId(),
                        tenantId);

                if (classInstances.size() > 0) {
                    // put chart data set calculations here
                    datasets.add(calcTasksPerDayPerTenant(classInstances, user.getId(), tenantId));
                }
            });
        });

        return datasets;
    }

    private XChartDataSet calcTasksPerDayPerTenant(List<ClassInstance> classInstances, String userId, String tenantId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Long> dataMap = classInstances.stream().collect(Collectors.groupingBy(ci -> {
            Date startingDate = new Date((Long) ci.getProperties().stream()
                    .filter(p -> p.getName().equals("Starting Date")).findAny().orElse(null).getValues().get(0));
            return dateFormat.format(startingDate);
        }, Collectors.counting()));

        List<XDataPoint> datapoints = new ArrayList<>();
        dataMap.entrySet().stream().map(entry -> new XDataPoint(entry.getKey(), entry.getValue()))
                .forEach(datapoints::add);

        XChartDataSet chartData = new XChartDataSet();
        chartData.setDatapoints(datapoints);
        chartData.setTimestamp(new Date());
        chartData.setName("Number of Tasks per Day");
        chartData.setUserId(userId);
        chartData.setTenantId(tenantId);

        return chartData;
        // chartDataRepository.insert(chartData);
    }
}
