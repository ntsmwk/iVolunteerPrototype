import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TaskEntryService {

  private apiUrl = '/rest/taskEntry';

  constructor(private http: HttpClient) {
  }

  findById(id: string) {
    return this.http.get([this.apiUrl, id].join('/'));
  }

}
