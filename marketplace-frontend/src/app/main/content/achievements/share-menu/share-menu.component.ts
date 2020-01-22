import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-share-menu',
  templateUrl: './share-menu.component.html',
  styleUrls: ['./share-menu.component.scss']
})
export class ShareMenuComponent implements OnInit {
  @Input() label: string;

  constructor() { }

  ngOnInit() {
  }

}
