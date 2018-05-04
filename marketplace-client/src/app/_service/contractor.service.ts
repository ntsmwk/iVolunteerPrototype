import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Source} from '../_model/source';
import {Task} from '../_model/task';

@Injectable()
export class ContractorService {

  constructor(private http: HttpClient) {
  }

  reserve(source: Source, task: Task) {
    return this.http.post('/trustifier/contractor/task/reserve', {source: source, task: task});
  }

}
