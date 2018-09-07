import {Component, OnInit} from '@angular/core';

import {Participant} from '../../_model/participant';
import {LoginService} from '../../_service/login.service';

@Component({
  selector: 'fuse-project-members',
  templateUrl: './project-members.component.html',
  styleUrls: ['./project-members.component.scss']
})
export class FuseProjectMembersComponent implements OnInit {

  friends = [
    {'name': 'Me', 'avatar': undefined, 'hours': '20.8'},
    {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg', 'hours': '12.4', 'resources': 'Strawberry Cake (2x)'},
    {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg', 'hours': '6.2', 'resources': 'Apple Pie (1x)'}
  ];

  constructor(private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.friends[0].avatar = `assets/images/avatars/${participant.username}.jpg`;
    });
  }

}
