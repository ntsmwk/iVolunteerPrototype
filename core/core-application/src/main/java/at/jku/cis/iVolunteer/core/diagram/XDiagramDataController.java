package at.jku.cis.iVolunteer.core.diagram;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramOrder;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.DiagramType;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay.ValueType;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramReturnEntity;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;

@RestController
@RequestMapping("/diagram")
public class XDiagramDataController {
    @Autowired
    LoginService loginService;
    @Autowired
    XDiagramDataRestClient diagramDataRestClient;
    @Autowired
    XDiagramDataService diagramDataService;

    @PostMapping("/refresh")
    public ResponseEntity<StringResponse> refresh(@RequestHeader("Authorization") String authorization) {
        CoreUser loggedInUser = loginService.getLoggedInUser();

        // TODO: refreshes all the diagram data sets for logged in user
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task")
    public XDiagramReturnEntity getTaskDiagramData() {
        // public XDiagramReturnEntity getTaskDiagramData(@RequestBody(required = false)
        // XDiagramFilter filter,
        // @RequestBody(required = false) XDiagramOrder order, @RequestBody
        // XDiagramDisplay display) {

        CoreUser loggedInUser = loginService.getLoggedInUser();

        XDiagramRawDataSet dataset = diagramDataService.getLatestDiagramData(loggedInUser);
        // filter
        // dataset = diagramDataService.filter(dataset, filter);

        // TODO just for testing
        XDiagramDisplay display = new XDiagramDisplay();
        display.setDiagramType(DiagramType.CATEGORY_ONLY);
        display.setValueType(ValueType.DURATION);

        diagramDataService.calcDiagramData(dataset, display);

        return null;
    }

    @PostMapping("/badge")
    public XDiagramReturnEntity getBadgeDiagramData() {

        return null;
    }

    @PostMapping("/compoetence")
    public XDiagramReturnEntity getCompetenceDiagramData() {

        return null;
    }

}
