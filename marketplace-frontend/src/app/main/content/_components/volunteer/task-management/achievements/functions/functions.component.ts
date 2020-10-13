import { Component, OnInit, AfterViewInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: "fuse-functions",
  templateUrl: "./functions.component.html",
  styleUrls: ["./functions.component.scss"],
  animations: fuseAnimations
})
export class FunctionsComponent implements OnInit, AfterViewInit {
  constructor() { }

  ngOnInit() { }

  ngAfterViewInit() { }
}
