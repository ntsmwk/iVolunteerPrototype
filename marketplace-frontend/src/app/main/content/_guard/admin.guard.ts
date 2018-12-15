import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {ParticipantRole} from '../_model/participant';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private loginService: LoginService) {
  }

  canActivate(): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      this.loginService.getLoggedInParticipantRole()
        .toPromise()
        .then((role: ParticipantRole) => resolve(role === 'ADMIN'));
    });
  }
}
