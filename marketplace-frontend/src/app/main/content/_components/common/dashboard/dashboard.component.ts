import { Component, OnInit } from "@angular/core";

import { LoginService } from "../../../_service/login.service";
import { ParticipantRole } from "../../../_model/user";
import { Router } from "@angular/router";

@Component({
  selector: "dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
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
