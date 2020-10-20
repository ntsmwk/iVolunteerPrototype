package at.jku.cis.iVolunteer.core.chart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartData;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@RestController
@RequestMapping("/chartdata")
public class XChartDataController {
    @Autowired
    LoginService loginService;
    @Autowired
    XChartDataRestClient chartDataRestClient;
    @Autowired
    CoreUserRepository coreUserRepository;

    @GetMapping
    public List<XChartData> getAllChartData(@RequestHeader("Authorization") String authorization) {
        CoreUser loggedInUser = loginService.getLoggedInUser();

        return chartDataRestClient.getChartDataByUser(authorization, loggedInUser);

    }

}
