import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'fuse-task-hierarchy',
  templateUrl: './task-hierarchy.component.html',
  styleUrls: ['./task-hierarchy.component.scss']
})
export class FuseTaskHierarchyComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    const taskId = this.route.paramMap['taskId'];
    console.log(taskId);

  }
}
