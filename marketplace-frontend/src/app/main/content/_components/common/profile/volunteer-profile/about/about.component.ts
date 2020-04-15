import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Participant } from "app/main/content/_model/participant";
import { LoginService } from "app/main/content/_service/login.service";

@Component({
  selector: "profile-about",
  templateUrl: "./about.component.html",
  styleUrls: ["./about.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class ProfileAboutComponent implements OnInit {
  volunteer: Participant;

  constructor(private loginService: LoginService) {}

  async ngOnInit() {
    this.volunteer = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
  }
}
