import {Component, OnDestroy, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../@fuse/animations';
import {Project} from '../_model/project';
import {Subscription} from 'rxjs';
import {Participant} from '../_model/participant';
import {Marketplace} from '../_model/marketplace';
import {ActivatedRoute, Router} from '@angular/router';
import {CoreVolunteerService} from '../_service/core-volunteer.service';
import {ProjectService} from '../_service/project.service';
import {LoginService} from '../_service/login.service';
import {MessageService} from '../_service/message.service';
import {isArray} from 'util';

@Component({
  selector: 'fuse-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.scss'],
  animations: fuseAnimations

})
export class FuseAchievementsComponent implements OnInit, OnDestroy {
  public projects: Array<Project>;
  private marketplaceChangeSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
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
              this.projectService.findFinished(marketplace)
                .toPromise().then((projects: Project[]) => this.projects = this.projects.concat(projects));
            });
        });
    });
  }
}

