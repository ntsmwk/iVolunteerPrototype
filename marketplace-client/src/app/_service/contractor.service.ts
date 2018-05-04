import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Source} from '../_model/source';
import {Task} from '../_model/task';
import {Participant} from '../_model/participant';

@Injectable()
export class ContractorService {

  constructor(private http: HttpClient) {
  }

  reserve(source: Source, task: Task) {
    return this.http.post('/trustifier/contractor/task/reserve', {source: source, task: task});
  }

  assign(source: Source, task: Task, participant: Participant) {
    return this.http.post('/trustifier/contractor/task/assign', {source: source, task: task, volunteer: participant});
  }
}
