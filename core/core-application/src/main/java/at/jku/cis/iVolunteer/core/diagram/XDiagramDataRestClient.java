package at.jku.cis.iVolunteer.core.diagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class XDiagramDataRestClient {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MarketplaceService marketplaceService;

    public List<XDiagramRawDataSet> getDiagramRawData() {
        List<Marketplace> marketplaces = marketplaceService.findAll();
        marketplaces = marketplaces.stream().distinct().collect(Collectors.toList());

        List<XDiagramRawDataSet> datasets = new ArrayList<>();
        marketplaces.forEach(mp -> {
            String preUrl = "{0}/diagramdata";
            String url = format(preUrl, mp.getUrl());
            try {
                ResponseEntity<XDiagramRawDataSet[]> resp = restTemplate.exchange(url, HttpMethod.GET,
                        buildEntity(null, null), XDiagramRawDataSet[].class);
                List<XDiagramRawDataSet> ret = Arrays.asList(resp.getBody());
                datasets.addAll(ret);
            } catch (Exception e) {
            }

        });

        return datasets;
    }

    public List<XDiagramRawDataSet> getDiagramRawDataByUser(String userId) {
        List<Marketplace> marketplaces = marketplaceService.findAll();
        marketplaces = marketplaces.stream().distinct().collect(Collectors.toList());

        List<XDiagramRawDataSet> datasets = new ArrayList<>();
        marketplaces.forEach(mp -> {
            String preUrl = "{0}/diagramdata/user/{1}";
            String url = format(preUrl, mp.getUrl(), userId);
            try {
                ResponseEntity<XDiagramRawDataSet[]> resp = restTemplate.exchange(url, HttpMethod.GET,
                        buildEntity(null, null), XDiagramRawDataSet[].class);
                List<XDiagramRawDataSet> ret = Arrays.asList(resp.getBody());
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
