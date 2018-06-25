import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {filter} from 'rxjs/internal/operators';

@Component({
  selector: 'fuse-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss'],
})
export class FuseTaskListComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.queryParams
      .pipe(filter(params => params.status))
      .subscribe(params => {
        console.log(params); // {order: "popular"}

        const status = params.status;
        console.log(status); // popular
      });
  }
}
