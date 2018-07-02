import {Component, OnInit} from '@angular/core';

import {Marketplace} from '../../content/_model/marketplace';
import {Participant} from '../../content/_model/participant';
import {CoreVolunteerService} from '../../content/_service/core.volunteer.service';
import {LoginService} from '../../content/_service/login.service';

import {isNullOrUndefined} from 'util';
import {SelectionModel} from '@angular/cdk/collections';
import {MessageService} from '../../content/_service/message.service';

@Component({
  selector: 'fuse-marketplace-selection',
  templateUrl: './marketplace-selection.component.html',
  styleUrls: ['./marketplace-selection.component.scss'],
})

export class FuseMarketplaceSelectionComponent implements OnInit {
  private role: string;
  public marketplaces: Marketplace[];

  private selection = new SelectionModel<Marketplace>(true, []);


  constructor(private loginService: LoginService,
              private messageService: MessageService,
              private volunterService: CoreVolunteerService) {
  }

  ngOnInit() {
    Promise.all([
      this.loginService.getLoggedIn().toPromise(),
      this.loginService.getLoggedInParticipantRole().toPromise()
    ]).then((values: any[]) => {
      this.role = <string> values[1];
      if (this.isRoleVolunteer(this.role)) {
        this.volunterService.findRegisteredMarketplaces((<Participant> values[0]).id)
          .toPromise().then((marketplaces: Marketplace[]) => this.marketplaces = marketplaces);
      }
    });
  }

  isSelected(marketplace: Marketplace) {
    return this.selection.isSelected(marketplace);
  }

  isLoggedInAsVolunteer() {
    return this.isRoleVolunteer(this.role);
  }

  private isRoleVolunteer(role: string) {
    return !isNullOrUndefined(role) && role === 'VOLUNTEER';
  }

  onMarketplaceSelect(marketplace: Marketplace) {
    console.log(marketplace);
    this.selection.toggle(marketplace);
    this.messageService.broadcast('marketplaceSelectionChanged', {});
    localStorage.setItem('marketplaces', JSON.stringify(this.selection.selected));
  }
}
