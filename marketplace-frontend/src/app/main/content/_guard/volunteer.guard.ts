import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {LoginService} from '../_service/login.service';

@Injectable({
  providedIn: 'root'
})
export class VolunteerGuard implements CanActivate {

  constructor(private loginService: LoginService) {
  }

  canActivate(): Promise<boolean> {
    const promise = new Promise<boolean>(resolve => {
      this.loginService.getLoggedInParticipantRole()
        .toPromise()
        .then((role) => resolve(role === 'VOLUNTEER'));
    });
    return promise;
  }
}
