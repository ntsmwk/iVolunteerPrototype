import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'fuse-task-timeline',
  templateUrl: './task-timeline.component.html',
  styleUrls: ['./task-timeline.component.scss']
})
export class FuseTaskTimelineComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    const taskId = this.route.paramMap['taskId'];
    console.log(taskId);
  }
}
