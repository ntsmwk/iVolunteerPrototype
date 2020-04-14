import { Component, OnInit } from "@angular/core";
import { Participant } from "app/main/content/_model/participant";
import { LoginService } from "app/main/content/_service/login.service";

@Component({
  selector: "volunteer-profile",
  templateUrl: "volunteer-profile.component.html"
})
export class VolunteerProfileComponent implements OnInit {
  volunteer: Participant;

  constructor(private loginService: LoginService) {}

  async ngOnInit() {
    this.volunteer = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
  }
}
