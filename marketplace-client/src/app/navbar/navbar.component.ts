import {Component, OnInit} from '@angular/core';
import {LoginGuard} from '../login/login.guard';
import {LoginService} from '../login/login.service';
import {Participant} from '../participant/participant';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit {

  participant: Participant;

  constructor(private loginGuard: LoginGuard,
              private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().subscribe((participant: Participant) => this.participant = participant);
  }

  isMenuVisible() {
    return !isNullOrUndefined(localStorage.getItem('token'));
  }

}
