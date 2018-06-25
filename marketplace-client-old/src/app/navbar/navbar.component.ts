import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginService} from '../_service/login.service';
import {Participant} from '../_model/participant';
import {isNullOrUndefined} from 'util';
import {MessageService} from '../_service/message.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit, OnDestroy {

  public participant: Participant;
  private loginSubscription: Subscription;
  private logoutSubscription: Subscription;
  participantRole;

  constructor(private loginService: LoginService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit() {
    this.getLoggedIn();
    this.loginSubscription = this.messageService.subscribe('login', this.getLoggedIn.bind(this));
    this.logoutSubscription = this.messageService.subscribe('logout', () => {
      this.participant = undefined;
      this.participantRole = undefined;
    });
  }

  ngOnDestroy() {
    this.loginSubscription.unsubscribe();
    this.logoutSubscription.unsubscribe();
  }

  isMenuVisible() {
    return !isNullOrUndefined(this.participant) && !isNullOrUndefined(this.participantRole);
  }

  private getLoggedIn() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant);
    this.loginService.getLoggedInParticipantRole().toPromise().then((participantRole) => this.participantRole = participantRole);
  }

  logout() {
    localStorage.removeItem('token');
    this.messageService.broadcast('logout', {});
    this.router.navigate(['login']);
  }

}
