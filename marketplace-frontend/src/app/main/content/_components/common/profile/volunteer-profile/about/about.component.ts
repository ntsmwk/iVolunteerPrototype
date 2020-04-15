import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "profile-about",
  templateUrl: "./about.component.html",
  styleUrls: ["./about.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class ProfileAboutComponent implements OnInit {
  about: any;

  constructor() {}

  ngOnInit(): void {}
}
