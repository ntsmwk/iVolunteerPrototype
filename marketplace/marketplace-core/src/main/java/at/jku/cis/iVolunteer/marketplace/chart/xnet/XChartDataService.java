package at.jku.cis.iVolunteer.marketplace.chart.xnet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserRepository;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartData;
import at.jku.cis.iVolunteer.model.chart.xnet.XDataPoint;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class XChartDataService {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClassInstanceRepository classInstanceRepository;
    @Autowired
    XChartDataRepository chartDataRepository;

    public List<XChartData> getChartData() {
        return chartDataRepository.findAll();
    }

    public XChartData getChartDataById(String id) {
        return chartDataRepository.findById(id);
    }

    public List<XChartData> getChartDataByUserId(String userId) {
        // List<XChartData> datasets = chartDataRepository.findByUserId(userId);
        // List<XChartData> result = new ArrayList<>();

        // HashSet<Object> seen = new HashSet<>();
        // datasets.removeIf(c -> !seen.add(Arrays.asList(c.getName(),
        // c.getTenantId())));

        // TODO: return only latest data sets per user
        // or delete old ones...

        // idea: sort by timestamp: 1. element = oldest
        // remove duplicates (name, tenantId)

        List<XChartData> datasets = chartDataRepository.findByUserId(userId);
        datasets.sort(Comparator.comparing(XChartData::getTimestamp).reversed());

        datasets = datasets.stream().distinct().collect(Collectors.toList());

        return datasets;
    }

    @Scheduled(fixedDelay = 1800000) // 30min
    public void generateChartDataSets() {
        List<User> users = userService.getUsersByRole(UserRole.VOLUNTEER);

        users.forEach(user -> {
            List<String> tenants = user.getSubscribedTenants().stream()
                    .filter(s -> s.getRole().equals(UserRole.VOLUNTEER)).map(s -> s.getTenantId()).distinct()
                    .collect(Collectors.toList());

            tenants.forEach(tenantId -> {
                List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(user.getId(),
                        tenantId);

                if (classInstances.size() > 0) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Map<String, Long> dataMap = classInstances.stream().collect(Collectors.groupingBy(ci -> {
                        Date startingDate = new Date(
                                (Long) ci.getProperties().stream().filter(p -> p.getName().equals("Starting Date"))
                                        .findAny().orElse(null).getValues().get(0));
                        return dateFormat.format(startingDate);
                    }, Collectors.counting()));

                    List<XDataPoint> datapoints = new ArrayList<>();
                    dataMap.entrySet().stream().map(entry -> new XDataPoint(entry.getKey(), entry.getValue()))
                            .forEach(datapoints::add);

                    XChartData chartData = new XChartData();
                    chartData.setDatapoints(datapoints);
                    chartData.setTimestamp(new Date());
                    chartData.setName("Number of Tasks per Day");
                    chartData.setUserId(user.getId());
                    chartData.setTenantId(tenantId);
                    chartDataRepository.insert(chartData);
                }
            });
        });
    }
}
