import {Component, OnInit} from '@angular/core';
import {SystemPing, SystemPingService} from '../../providers/system-ping.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  private systemPing: SystemPing;

  constructor(private systemPingService: SystemPingService) {
  }

  ngOnInit() {
    this.systemPingService.getSystemPing().toPromise()
      .then((systemPing: SystemPing) => this.systemPing = systemPing)
      .catch((error: any) => console.log(error));

  }

}
