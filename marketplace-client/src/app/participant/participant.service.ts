import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ParticipantService {

  private apiUrl = '/rest';

  constructor(private http: HttpClient) {
  }

  findVolunteerById(id: string) {
    return this.http.get([this.apiUrl, 'volunteer', id].join('/'));
  }



}
