import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { fuseAnimations } from "../../../../../../../../@fuse/animations";
import { Project } from "../../../../../_model/project";
import { Subscription } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { CoreVolunteerService } from "../../../../../_service/core-volunteer.service";
import { ProjectService } from "../../../../../_service/project.service";
import { LoginService } from "../../../../../_service/login.service";
import { MessageService } from "../../../../../_service/message.service";

@Component({
  selector: "fuse-achievements-fire-brigade",
  templateUrl: "./achievement-fire-brigade.component.html",
  styleUrls: ["./achievement-fire-brigade.component.scss"],
  animations: fuseAnimations
  // encapsulation: ViewEncapsulation.None
})
export class AchievementsFireBrigadeComponent implements OnInit, OnDestroy {
  public projects: Array<Project>;
  private marketplaceChangeSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private messageService: MessageService,
    private projectService: ProjectService,
    private coreVolunteerService: CoreVolunteerService
  ) {}

  ngOnInit() {
    // this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadProjects.bind(this));
  }

  ngOnDestroy() {
    // this.marketplaceChangeSubscription.unsubscribe();
  }
}
