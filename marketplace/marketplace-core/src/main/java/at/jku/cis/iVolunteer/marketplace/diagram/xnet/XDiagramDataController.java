package at.jku.cis.iVolunteer.marketplace.diagram.xnet;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;

@RestController
@RequestMapping("/diagramdata")
public class XDiagramDataController {
    @Autowired
    private XDiagramDataService diagramDataService;

    @GetMapping()
    public List<XDiagramRawDataSet> getAllDiagramData() {
        return diagramDataService.calcRawDataSets();
    }

    @GetMapping("/user/{userId}")
    public List<XDiagramRawDataSet> getAllDiagramDataPerUser(@PathVariable("userId") String userId) {
        return Collections.singletonList(diagramDataService.calcRawDataSetPerUser(userId));
    }
}