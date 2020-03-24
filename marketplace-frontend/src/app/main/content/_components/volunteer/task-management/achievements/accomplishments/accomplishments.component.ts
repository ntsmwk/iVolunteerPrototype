import { Component, OnInit, Input } from "@angular/core";
import { fuseAnimations } from "../../../../../../../../@fuse/animations";
import * as d3 from "d3";

@Component({
  selector: "fuse-accomplishments",
  templateUrl: "./accomplishments.component.html",
  styleUrls: ["./accomplishments.component.scss"],
  animations: fuseAnimations
})
export class AccomplishmentsComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
