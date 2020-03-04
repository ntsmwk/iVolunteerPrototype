import { Component, OnInit } from "@angular/core";

import { DisplayGrid, GridsterConfig } from "angular-gridster2";
import { CoreDashboardService } from "../../../_service/core-dashboard.service";
import { Dashboard } from "../../../_model/dashboard";
import { isNullOrUndefined } from "util";
import { LoginService } from "../../../_service/login.service";
import { ParticipantRole } from "../../../_model/participant";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageService } from "../../../_service/message.service";

@Component({
  selector: "dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"]
})
export class DashboardComponent implements OnInit {
  public role: ParticipantRole;

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit() {
    this.loginService
      .getLoggedInParticipantRole()
      .toPromise()
      .then((role: ParticipantRole) => {
        this.role = role;
        if (this.role === "RECRUITER") {
          this.router.navigate(["main/recruitment"]);
        }
        if (this.role === "ADMIN") {
          this.router.navigate(["main/marketplace/all"]);
        }
      });
  }
}
