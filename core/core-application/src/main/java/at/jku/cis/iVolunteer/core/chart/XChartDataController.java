package at.jku.cis.iVolunteer.core.chart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@RestController
@RequestMapping("/chartdata")
public class XChartDataController {
    @Autowired
    LoginService loginService;
    @Autowired
    XChartDataRestClient chartDataRestClient;
    @Autowired
    XChartDataService chartDataService;

    @GetMapping
    public List<XChartDataSet> getAllChartData(@RequestHeader("Authorization") String authorization) {
        CoreUser loggedInUser = loginService.getLoggedInUser();

        return chartDataService.getLatestChartData(loggedInUser);
        // return chartDataRestClient.getChartDataByUser(authorization, loggedInUser);
    }

}
