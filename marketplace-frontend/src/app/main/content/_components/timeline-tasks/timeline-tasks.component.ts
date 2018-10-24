import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from '../../_service/message.service';
import {LoginService} from '../../_service/login.service';
import {TaskService} from '../../_service/task.service';
import {CoreVolunteerService} from '../../_service/core-volunteer.service';

import {Volunteer} from '../../_model/volunteer';
import {Marketplace} from '../../_model/marketplace';
import {Task} from '../../_model/task';
import {Subscription} from 'rxjs';
import {isArray} from 'util';

@Component({
  templateUrl: './timeline-tasks.component.html',
  styleUrls: ['./timeline-tasks.component.scss']
})
export class FuseTimelineTasksComponent implements OnInit, OnDestroy {
  public timeline = {
    tasks: []
  };

  private marketplaces: Marketplace[] = [];
  private marketplaceChangeSubscription: Subscription;

  constructor(private messageService: MessageService,
              private loginService: LoginService,
              private taskService: TaskService,
              private volunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loadTasks();
    this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadTasks.bind(this));
  }

  private loadTasks() {
    this.timeline.tasks = new Array<Task>();
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {
      const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
      if (!isArray(selected_marketplaces)) {
        return;
      }
      this.volunteerService.findRegisteredMarketplaces(volunteer.id)
        .toPromise()
        .then((marketplaces: Marketplace[]) => {
          marketplaces
            .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
            .forEach(marketplace => {
              this.marketplaces = this.marketplaces.concat(marketplace);
              this.taskService.findByParticipant(marketplace, volunteer)
                .toPromise()
                .then((tasks: Array<Task>) => this.timeline.tasks = this.timeline.tasks.concat(tasks));
            });
        });
    });
  }

  getMarketplaceName(task: Task) {
    return this.marketplaces.filter(marketplace => marketplace.id === task.marketplaceId)[0].name;
  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }
}

