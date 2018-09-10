import {Component, Input, OnInit} from '@angular/core';
import {Marketplace} from '../../../_model/marketplace';
import {CoreVolunteerService} from '../../../_service/core-volunteer.service';
import {Participant} from '../../../_model/participant';


@Component({
  selector: 'fuse-profile-marketplaces',
  templateUrl: './marketplaces.component.html',
  styleUrls: ['./marketplaces.component.scss']
})
export class FuseProfileMarketplacesComponent implements OnInit {
  @Input('participant')
  private participant: Participant;

  public registeredMarketplaces: Array<Marketplace> = [];

  constructor(private volunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.volunteerService.findRegisteredMarketplaces(this.participant.id).toPromise().then((registeredMarketplaces: Marketplace[]) => {
      this.registeredMarketplaces = registeredMarketplaces;
    });
  }
}
