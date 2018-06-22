import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SourceService {

  private apiUrl = '/marketplace/source';

  constructor(private http: HttpClient) {
  }

  find() {
    return this.http.get(this.apiUrl);
  }

}
