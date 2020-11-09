package at.jku.cis.iVolunteer.core.chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.model.chart.xnet.XChartDataSet;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class XChartDataRestClient {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MarketplaceService marketplaceService;

    // TODO
    // call to marketplace without authentication (excepted in mp WebSecurityConfig)
    public List<XChartDataSet> getChartDataFromMarketplaces() {
        List<Marketplace> marketplaces = marketplaceService.findAll();
        marketplaces = marketplaces.stream().distinct().collect(Collectors.toList());

        List<XChartDataSet> datasets = new ArrayList<>();
        marketplaces.forEach(mp -> {
            String preUrl = "{0}/chartdata";
            String url = format(preUrl, mp.getUrl());
            try {
                ResponseEntity<XChartDataSet[]> resp = restTemplate.exchange(url, HttpMethod.GET,
                        buildEntity(null, null), XChartDataSet[].class);
                List<XChartDataSet> ret = Arrays.asList(resp.getBody());
                datasets.addAll(ret);
            } catch (Exception e) {
            }

        });

        return datasets;
    }

    private HttpEntity<?> buildEntity(Object body, String authorization) {
        return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
    }

    private HttpHeaders buildAuthorizationHeader(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

}