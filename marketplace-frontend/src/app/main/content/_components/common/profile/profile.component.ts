import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { UserRole } from "app/main/content/_model/user";
import { LoginService } from "app/main/content/_service/login.service";
import { Router } from "@angular/router";

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  public role: UserRole;

  constructor(private loginService: LoginService, private router: Router) {}

  async ngOnInit() {
    this.role = <UserRole>(
      await this.loginService.getLoggedInUserRole().toPromise()
    );
  }
}
