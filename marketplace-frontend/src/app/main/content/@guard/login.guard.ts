import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { isNullOrUndefined } from 'util';
import { LoginService } from '../_service/login.service';
import { ParticipantRole } from '../_model/participant';

@Injectable({
    providedIn: 'root'
})
export class LoginGuard implements CanActivate {

    constructor(
        private router: Router,
        private loginService: LoginService) {
    }

    canActivate(): Promise<boolean> {
        return new Promise<boolean>(resolve => {
            this.loginService.getLoggedInParticipantRole()
                .toPromise()
                .then((role: ParticipantRole) => {
                    resolve(role == "HELP_SEEKER" || role == "VOLUNTEER");
                });
        });
    }
}
