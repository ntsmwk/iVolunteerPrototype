import {Component, OnInit} from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';
import {LoginService} from '../../_service/login.service';
import {Participant} from '../../_model/participant';
import {CoreMarketplaceService} from '../../_service/core-marketplace.service';
import {CoreVolunteerService} from '../../_service/core-volunteer.service';
import {Marketplace} from '../../_model/marketplace';
import {Volunteer} from '../../_model/volunteer';
import {MessageService} from '../../_service/message.service';
import {ArrayService} from '../../_service/array.service';

@Component({
  selector: 'fuse-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.scss'],
  animations: fuseAnimations

})
export class FuseSuggestionsComponent implements OnInit {
  radioOptions = 'test1';

  private volunteer: Participant;
  public marketplaces = new Array<Marketplace>();
  public projects = {
    'projects': [
      {
        'name': 'Project 1',
        'description': 'Description of Project 1',
        'marketplace': 'Marketplace 1',
        'startDate': '10.08.2018  07:00',
        'endDate': '10.08.2018  18:00',
        'tasks': [{'name': 'Task 1'}, {'name': 'Task 2'}]
      },
      {
        'name': 'Project 2',
        'description': 'Description of Project 1',
        'marketplace': 'Marketplace 1',
        'startDate': '21.08.2018  08:00',
        'endDate': '23.08.2018  12:00',
        'tasks': [{'name': 'Task 1'}, {'name': 'Task 2'}]
      }

    ]
  };

  constructor(private arrayService: ArrayService,
              private loginService: LoginService,
              private messageService: MessageService,
              private marketplaceService: CoreMarketplaceService,
              private volunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.volunteer = participant as Volunteer;
      this.loadSuggestedMarketplaces();
    });
  }

  private loadSuggestedMarketplaces() {
    Promise.all([
      this.marketplaceService.findAll().toPromise(),
      this.volunteerService.findRegisteredMarketplaces(this.volunteer.id).toPromise()
    ]).then((values: any[]) => {
      this.marketplaces = this.arrayService.removeAll(values[0], values[1]);
      console.log(this.marketplaces);
    });
  }

  registerMarketplace(marketplace) {
    this.volunteerService.registerMarketplace(this.volunteer.id, marketplace.id).toPromise().then(() => {
      this.loadSuggestedMarketplaces();
      this.messageService.broadcast('marketplaceRegistration', {});
    });
  }

}
