import { Component, OnDestroy, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../../@fuse/animations";
import { LoginService } from "../../../../../_service/login.service";
import { isArray } from "util";
import { ProjectService } from "../../../../../_service/project.service";
import { Project } from "../../../../../_model/project";
import { ArrayService } from "../../../../../_service/array.service";
import { Subscription } from "rxjs";
import { Marketplace } from "../../../../../_model/marketplace";
import { CoreVolunteerService } from "../../../../../_service/core-volunteer.service";
import { Participant } from "../../../../../_model/participant";
import { MessageService } from "../../../../../_service/message.service";
import { MarketplaceService } from "../../../../../_service/core-marketplace.service";
import { Volunteer } from "../../../../../_model/volunteer";

@Component({
  selector: "fuse-recommendations",
  templateUrl: "./recommendations.component.html",
  styleUrls: ["./recommendations.component.scss"],
  animations: fuseAnimations,
})
export class RecommendationsComponent implements OnInit, OnDestroy {
  private marketplaceChangeSubscription: Subscription;

  private volunteer: Participant;
  public projects = new Array<Project>();
  public marketplaces = new Array<Marketplace>();

  constructor(
    private arrayService: ArrayService,
    private loginService: LoginService,
    private projectService: ProjectService,
    private messageService: MessageService,
    private marketplaceService: MarketplaceService,
    private volunteerService: CoreVolunteerService
  ) {}

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((participant: Participant) => {
        this.volunteer = participant as Volunteer;
        this.loadSuggestedProjects();
        this.loadSuggestedMarketplaces();
      });
    this.marketplaceChangeSubscription = this.messageService.subscribe(
      "marketplaceSelectionChanged",
      this.loadSuggestedProjects.bind(this)
    );
  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

  private loadSuggestedMarketplaces() {
    Promise.all([
      this.marketplaceService.findAll().toPromise(),
      this.volunteerService
        .findRegisteredMarketplaces(this.volunteer.id)
        .toPromise(),
    ]).then((values: any[]) => {
      this.marketplaces = this.arrayService.removeAll(values[0], values[1]);
    });
  }

  registerMarketplace(marketplace) {
    // TODO OLD!!
    this.volunteerService
      .subscribeTenant(this.volunteer.id, marketplace.id, "")
      .toPromise()
      .then(() => {
        this.loadSuggestedMarketplaces();
        this.messageService.broadcast("marketplaceRegistration", {});
      });
  }

  private loadSuggestedProjects() {
    this.projects = new Array<Project>();
    const selected_marketplaces = JSON.parse(
      localStorage.getItem("marketplaces")
    );
    if (!isArray(selected_marketplaces)) {
      return;
    }
    this.volunteerService
      .findRegisteredMarketplaces(this.volunteer.id)
      .toPromise()
      .then((marketplaces: Marketplace[]) => {
        marketplaces
          .filter((mp) =>
            selected_marketplaces.find(
              (selected_mp) => selected_mp.id === mp.id
            )
          )
          .forEach((marketplace) => {
            this.projectService
              .findAvailable(marketplace)
              .toPromise()
              .then(
                (projects: Project[]) =>
                  (this.projects = this.projects.concat(projects))
              );
          });
      });
  }
}
