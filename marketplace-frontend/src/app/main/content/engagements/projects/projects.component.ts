import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginService} from '../../_service/login.service';
import {Participant} from '../../_model/participant';
import {Marketplace} from '../../_model/marketplace';
import {CoreVolunteerService} from '../../_service/core-volunteer.service';
import {ProjectService} from '../../_service/project.service';
import {Project} from '../../_model/project';
import {MessageService} from '../../_service/message.service';
import {Subscription} from 'rxjs';
import {isArray} from 'util';

@Component({
  selector: 'fuse-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss'],
})
export class FuseProjectsComponent implements OnInit, OnDestroy {

  public projects: Project[];
  private marketplaceChangeSubscription: Subscription;

  constructor(private loginService: LoginService,
              private messageService: MessageService,
              private projectService: ProjectService,
              private coreVolunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loadProjects();
    this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadProjects.bind(this));
  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

  private loadProjects() {
    this.projects = [];
    this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
      const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
      if (!isArray(selected_marketplaces)) {
        return;
      }
      this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id)
        .toPromise()
        .then((marketplaces: Marketplace[]) => {
          marketplaces
            .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
            .forEach(marketplace => {
              this.projectService.findAllByVolunteer(marketplace, volunteer.id)
                .toPromise()
                .then((projects: Project[]) => this.projects = this.projects.concat(projects));
            });
        });
    });
  }
}
