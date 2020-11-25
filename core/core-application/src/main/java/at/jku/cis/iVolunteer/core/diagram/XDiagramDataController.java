package at.jku.cis.iVolunteer.core.diagram;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model.badge.XBadgeCertificate;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramData;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramDataPointBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.XDiagramReturnEntity;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramDisplayBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramFilterBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramOrderBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.badge.XDiagramOrderBadge.OrderTypeBadge;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramDisplayTask;
import at.jku.cis.iVolunteer.model.diagram.xnet.data.task.XDiagramFilterTask;

@RestController
@RequestMapping("/diagram")
public class XDiagramDataController {
    @Autowired
    LoginService loginService;
    @Autowired
    XDiagramDataService diagramDataService;

    @GetMapping("/refresh")
    public ResponseEntity<StringResponse> refresh() {
        CoreUser loggedInUser = loginService.getLoggedInUser();

        diagramDataService.queryDiagramRawDataSetsFromMarketplacesByUser(loggedInUser.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task")
    public ResponseEntity<Object> getTaskDiagramData(@RequestBody XDiagramPayloadTask payload) {
        if (payload == null) {
            return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL),
                    HttpStatus.BAD_REQUEST);
        }

        XDiagramFilterTask filter = payload.getFilter();
        XDiagramDisplayTask display = payload.getDisplay();
        boolean order = payload.getOrderAsc();

        if (display == null) {
            return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL),
                    HttpStatus.BAD_REQUEST);
        }

        CoreUser loggedInUser = loginService.getLoggedInUser();
        XDiagramRawDataSet dataset = diagramDataService.getLatestDiagramRawData(loggedInUser);

        if (dataset.getDatapoints() == null) {
            diagramDataService.queryDiagramRawDataSetsFromMarketplacesByUser(loggedInUser.getId());
            dataset = diagramDataService.getLatestDiagramRawData(loggedInUser);
        }

        if (filter != null) {
            dataset = diagramDataService.filter(dataset, filter);
        }

        XDiagramReturnEntity diagramData = diagramDataService.generateSunburstData(dataset, display);

        // TODO Philipp: order all levels downwards
        if (order) {
            diagramData.getData()
                    .sort(Comparator.comparing(XDiagramData::getName, Collator.getInstance(Locale.GERMAN)));
        } else {
            diagramData.getData()
                    .sort(Comparator.comparing(XDiagramData::getName, Collator.getInstance(Locale.GERMAN)).reversed());
        }

        return ResponseEntity.ok(diagramData);
    }

    @PostMapping("/badge")
    public ResponseEntity<Object> getBadgeDiagramData(@RequestBody XDiagramPayloadBadge payload) {
        if (payload == null) {
            return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL),
                    HttpStatus.BAD_REQUEST);
        }

        XDiagramOrderBadge order = payload.getOrder();
        XDiagramFilterBadge filter = payload.getFilter();
        XDiagramDisplayBadge display = payload.getDisplay();

        if (display == null) {
            return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL),
                    HttpStatus.BAD_REQUEST);
        }

        CoreUser loggedInUser = loginService.getLoggedInUser();
        XDiagramRawDataSet dataset = diagramDataService.getLatestDiagramRawData(loggedInUser);

        if (dataset.getDatapoints() == null) {
            diagramDataService.queryDiagramRawDataSetsFromMarketplacesByUser(loggedInUser.getId());
            dataset = diagramDataService.getLatestDiagramRawData(loggedInUser);
        }

        if (filter != null) {
            dataset = diagramDataService.filter(dataset, filter);
        }

        XDiagramReturnEntity diagramData = diagramDataService.generateBadgeTimelineData(dataset.getBadges());

        if (order == null) {
            // set default value
            order = new XDiagramOrderBadge();
            order.setOrderAsc(true);
            order.setOrderType(OrderTypeBadge.ORDER_CREATIONDATE);
        }

        final XDiagramOrderBadge a = order;
        diagramData.getData().forEach(d -> {
            XDiagramDataPointBadge e = (XDiagramDataPointBadge) d;
            switch (a.getOrderType()) {
                case ORDER_CREATIONDATE:
                    if (a.isOrderAsc()) {
                        e.getBadges().sort(Comparator.comparing(x -> x.getIssueDate()));
                    } else {
                        e.getBadges()
                                .sort(Comparator.comparing(x -> ((XBadgeCertificate) x).getIssueDate()).reversed());
                    }
                    break;
                case ORDER_TENANT_ID:
                    if (a.isOrderAsc()) {
                        e.getBadges().sort(Comparator.comparing(x -> x.getTenantSerialized().getId()));
                    } else {
                        e.getBadges().sort(Comparator
                                .comparing(x -> ((XBadgeCertificate) x).getTenantSerialized().getId()).reversed());
                    }
                    break;
                case ORDER_TENANT_NAME:
                    if (a.isOrderAsc()) {
                        e.getBadges().sort(Comparator.comparing(x -> x.getTenantSerialized().getName(),
                                Collator.getInstance(Locale.GERMAN)));
                    } else {
                        e.getBadges()
                                .sort(Comparator.comparing(x -> ((XBadgeCertificate) x).getTenantSerialized().getName(),
                                        Collator.getInstance(Locale.GERMAN)).reversed());
                    }
                    break;
                case ORDER_BADGE_NAME:
                    if (a.isOrderAsc()) {
                        e.getBadges().sort(Comparator.comparing(x -> x.getBadgeSerialized().getName(),
                                Collator.getInstance(Locale.GERMAN)));
                    } else {
                        e.getBadges()
                                .sort(Comparator.comparing(x -> ((XBadgeCertificate) x).getBadgeSerialized().getName(),
                                        Collator.getInstance(Locale.GERMAN)).reversed());
                    }
                    break;
            }

        });

        return ResponseEntity.ok(diagramData);
    }

    @PostMapping("/competence")
    public XDiagramReturnEntity getCompetenceDiagramData() {
        return null;
    }

}
