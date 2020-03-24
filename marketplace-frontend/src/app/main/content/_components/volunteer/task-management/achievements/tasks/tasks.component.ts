import { Component, OnInit, ViewChild, Input, SimpleChanges } from '@angular/core';
import { fuseAnimations } from '../../../../../../../../@fuse/animations';
import { Marketplace } from '../../../../../_model/marketplace';
import { ClassInstanceDTO } from '../../../../../_model/meta/Class';
import { Volunteer } from '../../../../../_model/volunteer';

@Component({
  selector: 'fuse-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss'],
  animations: fuseAnimations
})

export class TasksComponent implements OnInit {
  volunteer: Volunteer;
  marketplace: Marketplace;

  @Input() classInstanceDTOs: ClassInstanceDTO[];
  selectedYaxis: string;
  selectedYear: string;
  selectedTaskType: string;
  timelineFilter: { from: Date, to: Date };


  constructor() {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    // console.error('tasks', changes);

    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case 'classInstanceDTOs': {
            if (typeof changes.classInstanceDTOs.currentValue != 'undefined') {
              this.classInstanceDTOs = changes.classInstanceDTOs.currentValue;
            }
          }
        }
      }
    }
  }

  
}