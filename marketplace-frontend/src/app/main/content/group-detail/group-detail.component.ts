import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  templateUrl: './group-detail.component.html',
  styleUrls: ['./group-detail.component.scss']
})
export class FuseGroupDetailComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadGroup(params['groupId']));
  }

  private loadGroup(groupId: string) {
    console.log(groupId);

  }
}
