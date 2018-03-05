import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginGuard} from '../login/login.guard';
import {LoginService} from '../login/login.service';
import {Participant} from '../participant/participant';
import {isNullOrUndefined} from 'util';
import {MessageService} from '../_service/message.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit, OnDestroy {

  public participant: Participant;
  private loginSubscription: Subscription;
  private logoutSubscription: Subscription;
  participantRole;

  constructor(private loginGuard: LoginGuard,
              private loginService: LoginService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.loginService.getLoggedInParticipantRole().toPromise().then((role) => this.participantRole = role);

    this.getLoggedIn();
    this.loginSubscription = this.messageService.subscribe('login', this.getLoggedIn.bind(this));
    this.logoutSubscription = this.messageService.subscribe('logout', () => this.participant = undefined);
  }

  ngOnDestroy() {
    this.loginSubscription.unsubscribe();
    this.logoutSubscription.unsubscribe();
  }

  isMenuVisible() {
    return !isNullOrUndefined(localStorage.getItem('token'));
  }

  private getLoggedIn() {
    this.loginService.getLoggedIn()
      .toPromise()
      .then((participant: Participant) => this.participant = participant);
  }
}
