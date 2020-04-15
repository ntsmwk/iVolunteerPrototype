import { Component, OnInit } from "@angular/core";
import { Participant } from "app/main/content/_model/participant";
import { LoginService } from "app/main/content/_service/login.service";
import { fuseAnimations } from "@fuse/animations";
import { ImageService } from "app/main/content/_service/image.service";

@Component({
  selector: "volunteer-profile",
  templateUrl: "volunteer-profile.component.html",
  styleUrls: ["volunteer-profile.component.scss"],
  animations: fuseAnimations
})
export class VolunteerProfileComponent implements OnInit {
  volunteer: Participant;

  constructor(
    private loginService: LoginService,
    private imageService: ImageService
  ) {}

  async ngOnInit() {
    this.volunteer = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
  }

  getProfileImage() {
    return this.imageService.getImgSourceFromBytes(this.volunteer.image);
  }
}
