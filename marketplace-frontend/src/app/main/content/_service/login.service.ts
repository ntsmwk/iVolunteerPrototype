import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  getLoggedIn() {
    return this.http.get('/login');
  }

  getLoggedInParticipantRole() {
    return this.http.get('/login/role');
  }

  login(username: string, password: string) {
    return this.http.post('/login', {username: username, password: password}, {observe: 'response'});
  }

}
