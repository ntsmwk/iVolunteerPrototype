import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {ParticipantRole} from '../_model/participant';

@Injectable({
  providedIn: 'root'
})
export class HelpSeekerGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      this.loginService.getLoggedInParticipantRole()
        .toPromise()
        .then((role: ParticipantRole) => {
          resolve(role === 'HELP_SEEKER');
        });
    });
  }
}
