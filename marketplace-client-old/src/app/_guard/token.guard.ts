import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {isNullOrUndefined} from 'util';

@Injectable()
export class TokenGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (isNullOrUndefined(token) || token.length === 0) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
