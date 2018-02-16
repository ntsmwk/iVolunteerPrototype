import {Component, OnInit} from '@angular/core';
import {SystemPing, SystemPingService} from '../../providers/system-ping.service';
import {Router} from '@angular/router';
import {isNullOrUndefined} from 'util';


@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  personId: string | undefined;
  personType: string | undefined;

  constructor(private router: Router,
              private systemPingService: SystemPingService) {
  }

  ngOnInit() {
    this.systemPingService.getSystemPing().toPromise()
      .then((systemPing: SystemPing) => {
        if (isNullOrUndefined(systemPing) || isNullOrUndefined(systemPing.participant)) {
          this.router.navigate(['login'])
        } else {
          this.personId = this.extractId(systemPing.participant);
          this.personType = this.extractType(systemPing.participant);
        }
      })
      .catch((error: any) => this.router.navigate(['error']));

  }

  private extractId(participant: string | undefined) {
    return participant.split('#')[1];
  }

  private extractType(participant: string | undefined) {
    return participant.split('#')[0].replace('at.jku.cis.', '');
  }
}


