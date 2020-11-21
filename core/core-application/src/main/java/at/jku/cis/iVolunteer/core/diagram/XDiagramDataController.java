package at.jku.cis.iVolunteer.core.diagram;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramDisplay;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramFilter;
import at.jku.cis.iVolunteer.model.diagram.xnet.XDiagramOrder;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramData;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramReturnEntity;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;

@RestController
@RequestMapping("/diagram")
public class XDiagramDataController {
    @Autowired
    LoginService loginService;
    @Autowired
    XDiagramDataService diagramDataService;

    @PostMapping("/refresh")
    public ResponseEntity<StringResponse> refresh(@RequestHeader("Authorization") String authorization) {
        CoreUser loggedInUser = loginService.getLoggedInUser();

        diagramDataService.queryDiagramRawDataSetsFromMarketplacesByUser(loggedInUser.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task")
    public ResponseEntity<Object> getTaskDiagramData(@RequestBody XDiagramPayload payload) {
        XDiagramFilter filter = payload.getFilter();
        XDiagramOrder order = payload.getOrder();
        XDiagramDisplay display = payload.getDisplay();

        if (display == null) {
            return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NO_DISPLAY), HttpStatus.BAD_REQUEST);
        }

        CoreUser loggedInUser = loginService.getLoggedInUser();
        XDiagramRawDataSet dataset = diagramDataService.getLatestDiagramData(loggedInUser);

        if (dataset.getDatapoints() == null) {
            diagramDataService.queryDiagramRawDataSetsFromMarketplacesByUser(loggedInUser.getId());
            dataset = diagramDataService.getLatestDiagramData(loggedInUser);
        }

        if (filter != null) {
            dataset = diagramDataService.filter(dataset, filter);
        }

        XDiagramReturnEntity diagramData = diagramDataService.generateSunburstData(dataset, display);

        if (order != null) {
            if (order.isAsc()) {
                diagramData.getData()
                        .sort(Comparator.comparing(XDiagramData::getName, Collator.getInstance(Locale.GERMAN)));
            } else {
                diagramData.getData().sort(
                        Comparator.comparing(XDiagramData::getName, Collator.getInstance(Locale.GERMAN)).reversed());
            }

        }

        return ResponseEntity.ok(diagramData);

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
