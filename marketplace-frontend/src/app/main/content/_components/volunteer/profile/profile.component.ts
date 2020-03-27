import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "volunteer-profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations
})
export class VolunteerProfileComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
