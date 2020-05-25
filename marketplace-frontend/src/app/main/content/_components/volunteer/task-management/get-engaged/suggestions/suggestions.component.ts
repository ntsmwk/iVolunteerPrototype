import { Component, OnDestroy, OnInit } from "@angular/core";
import { fuseAnimations } from "../../../../../../../../@fuse/animations";
import { LoginService } from "../../../../../_service/login.service";
import { Participant } from "../../../../../_model/participant";
import { MarketplaceService } from "../../../../../_service/core-marketplace.service";
import { CoreVolunteerService } from "../../../../../_service/core-volunteer.service";
import { Marketplace } from "../../../../../_model/marketplace";
import { Volunteer } from "../../../../../_model/volunteer";
import { MessageService } from "../../../../../_service/message.service";
import { ArrayService } from "../../../../../_service/array.service";
import { Project } from "../../../../../_model/project";
import { isArray } from "util";
import { ProjectService } from "../../../../../_service/project.service";
import { Subscription } from "rxjs";

@Component({
  selector: "fuse-suggestions",
  templateUrl: "./suggestions.component.html",
  styleUrls: ["./suggestions.component.scss"],
  animations: fuseAnimations,
})
export class SuggestionsComponent implements OnInit {
  suggestions = {
    marketplaces: [
      {
        suggestion: "Marketplace A",
        name: "Garry Newman",
        avatar: "assets/images/backgrounds/winter.jpg",
      },
      {
        suggestion: "Marketplace B",
        name: "Katherine Green",
        avatar: "assets/images/backgrounds/summer.jpg",
      },
    ],
    projects: [
      {
        suggestion: "Project X",
        name: "Danielle Jackson",
        avatar: "assets/images/backgrounds/spring.jpg",
      },
    ],
  };

  ngOnInit() {}
}
