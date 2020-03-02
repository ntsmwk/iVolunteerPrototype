import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../../../@fuse/animations';
import {LoginService} from '../../../../_service/login.service';
import {isArray, isNullOrUndefined} from 'util';
import {ProjectService} from '../../../../_service/project.service';
import {Project} from '../../../../_model/project';
import {Subscription} from 'rxjs';
import {Marketplace} from '../../../../_model/marketplace';
import {CoreVolunteerService} from '../../../../_service/core-volunteer.service';
import {MessageService} from '../../../../_service/message.service';
import {Participant} from '../../../../_model/participant';
import {Volunteer} from '../../../../_model/volunteer';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'fuse-opportunities',
  templateUrl: './opportunities.component.html',
  styleUrls: ['./opportunities.component.scss'],
  animations: fuseAnimations

})
export class OpportunitiesComponent implements OnInit {
  @Input('projects')
  public projects: Array<Project>;
  public participant: Participant;
  public registeredMarketplaces: Array<Marketplace> = [];


  constructor(private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService,
              private projectService: ProjectService,
              private coreVolunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.coreVolunteerService.findRegisteredMarketplaces(this.participant.id).toPromise().then((registeredMarketplaces: Marketplace[]) => {
        this.registeredMarketplaces = registeredMarketplaces;
      });
    });
  }


  public calculateProgess(project: Project) {
    return 50;
  }

  public unregisterMarketplace(marketplace: Marketplace) {

  }
}
