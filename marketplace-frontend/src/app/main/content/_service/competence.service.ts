import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class CompetenceService {

  private apiUrl = '/core/competence';

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(this.apiUrl);
  }

}
