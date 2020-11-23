import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "customizable-header",
  templateUrl: "header.component.html"
})
export class HeaderComponent implements OnInit {
  @Input() headerText: string;
  @Input() displayNavigateBack: boolean;

  constructor() { }

  async ngOnInit() {
  }

  navigateBack() {
    window.history.back();
  }
}
