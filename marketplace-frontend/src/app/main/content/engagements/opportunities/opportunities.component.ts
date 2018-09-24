import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';
import {LoginService} from '../../_service/login.service';
import {isArray, isNullOrUndefined} from 'util';
import {ProjectService} from '../../_service/project.service';
import {Project} from '../../_model/project';
import {Subscription} from 'rxjs';
import {Marketplace} from '../../_model/marketplace';
import {CoreVolunteerService} from '../../_service/core-volunteer.service';
import {MessageService} from '../../_service/message.service';
import {Participant} from '../../_model/participant';
import {Volunteer} from '../../_model/volunteer';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'fuse-opportunities',
  templateUrl: './opportunities.component.html',
  styleUrls: ['./opportunities.component.scss'],
  animations: fuseAnimations

})
export class OpportunitiesComponent implements OnInit, OnDestroy {
  private mySelf: boolean;

  public participant: Participant;
  public registeredMarketplaces: Array<Marketplace> = [];


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

    this.route.params.subscribe((params) => {
      const participantId = params['participantId'];
      if (isNullOrUndefined(participantId)) {
        this.mySelf = true;
        this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant);
      } else {
        this.mySelf = false;
        this.coreVolunteerService.findById(participantId).toPromise().then((volunteer: Volunteer) => this.participant = volunteer);
      }

      this.coreVolunteerService.findRegisteredMarketplaces(this.participant.id).toPromise().then((registeredMarketplaces: Marketplace[]) => {
        this.registeredMarketplaces = registeredMarketplaces;
      });
    });




  }

  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

  private loadProjects() {
    this.projects = new Array<Project>();
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
              this.projectService.findEngaged(marketplace)
                .toPromise().then((projects: Project[]) => this.projects = this.projects.concat(projects));
            });
        });
    });
  }

  public calculateProgess(project: Project) {
    return 50;
  }
}
