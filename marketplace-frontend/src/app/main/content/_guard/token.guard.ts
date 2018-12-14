import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {isNullOrUndefined} from 'util';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class TokenGuard implements CanActivate {

  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (isNullOrUndefined(token) || token.length === 0 || this.jwtHelper.isTokenExpired(token)) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
