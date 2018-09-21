import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ParticipantRole } from '../_model/participant';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  getLoggedIn() {
    return this.http.get('/core/login');
  }

  getLoggedInParticipantRole() {
    return this.http.get('/core/login/role');
  }

  login(username: string, password: string) {
    return this.http.post('/core/login', {username: username, password: password}, {observe: 'response'});
  }
}
