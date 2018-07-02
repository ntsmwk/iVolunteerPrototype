import {Component, OnInit} from '@angular/core';

import {Participant} from '../../content/_model/participant';
import {LoginService} from '../../content/_service/login.service';

@Component({
  selector: 'fuse-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.scss']
})

export class FuseUserMenuComponent implements OnInit {

  participant: Participant;

  constructor(private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant);
  }

  logout() {
    localStorage.clear();
    window.location.reload(true);
  }

}
