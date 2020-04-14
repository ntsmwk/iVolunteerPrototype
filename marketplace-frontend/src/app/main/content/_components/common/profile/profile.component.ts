import { Component, OnInit } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations
})
export class ProfileComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
