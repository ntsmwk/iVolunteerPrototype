import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BCEntry} from '../_model/bcEntry';
import {isNullOrUndefined} from 'util';

@Injectable({
  providedIn: 'root'
})
export class CoreBCEntryService {

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get(`/core/bcEntry`);
  }


  findById(id: string) {
    return this.http.get(`/core/bcEntry/${id}`);
  }


  save(bcEntry: BCEntry) {
    if (isNullOrUndefined(bcEntry.id)) {
      return this.http.post(`/core/bcEntry`, bcEntry);
    }
    return this.update(bcEntry);
  }

  update(bcEntry: BCEntry) {
    return this.http.put(`/core/bcEntry/${bcEntry.id}`, bcEntry);
  }

  delete(bcEntry: BCEntry) {
    return this.http.delete(`/core/bcEntry/${bcEntry.id}`);
  }

}
