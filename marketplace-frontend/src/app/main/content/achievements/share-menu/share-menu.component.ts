import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-share-menu',
  templateUrl: './share-menu.component.html',
  styleUrls: ['./share-menu.component.scss']
})
export class ShareMenuComponent implements OnInit {
  @Input() label: string;
  @Output() export = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  exportChart() {
    this.export.emit(this.label);
  }

}
