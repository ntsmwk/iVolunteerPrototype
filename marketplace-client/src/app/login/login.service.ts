import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  getLoggedIn() {
    return this.http.get('/rest/login');
  }

  getLoggedInParticipantRole() {
    return this.http.get('/rest/login/role');
  }

  login(username: string, password: string) {
    return this.http.post('/rest/login', {username: username, password: password}, {observe: 'response'});
  }

}
