import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Configuration} from './configuration';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AuthenticationService {

  private serverUrl: string;

  constructor(private http: HttpClient, _configuration: Configuration) {
    this.serverUrl = _configuration.Server;
  }

  login(loginData: LoginData): Observable<any> {
    console.log('Login ' + this.serverUrl + '/auth/github' + ' ' + loginData);
    return this.http.get(this.serverUrl + '/auth/github', {
      withCredentials: true,
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
      }
    }) as Observable<any>;
  }

  loginCallback(): Observable<any> {
    console.log('Login ' + this.serverUrl + '/auth/github/callback');
    return this.http.get(this.serverUrl + '/auth/github/callback') as Observable<any>;
  }
}

export class LoginData {
  username: string;
  password: string;
}
