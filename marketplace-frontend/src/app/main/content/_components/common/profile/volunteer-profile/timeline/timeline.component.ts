import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";

import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "profile-timeline",
  templateUrl: "./timeline.component.html",
  styleUrls: ["./timeline.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class ProfileTimelineComponent implements OnInit {
  timeline: any;

  constructor() {}

  ngOnInit(): void {}
}
