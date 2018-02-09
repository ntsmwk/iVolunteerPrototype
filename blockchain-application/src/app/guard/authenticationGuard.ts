import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(private cookieServie: CookieService,
              private router: Router) {
  }

  canActivate(): boolean {
    if (this.cookieServie.check('access_token')) {
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }
}
