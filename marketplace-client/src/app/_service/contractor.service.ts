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
    const body = {source: source, task: task};
    return this.http.post('/trustifier/contractor/task/reserve', body, {responseType: 'text'});
  }

  assign(source: Source, task: Task, participant: Participant) {
    const body = {source: source, task: task, volunteer: participant};
    return this.http.post('/trustifier/contractor/task/assign', body, {responseType: 'text'});
  }

  finish(source: Source, task: Task) {
    const body = {source: source, task: task};
    return this.http.post('/trustifier/contractor/task/finish', body, {responseType: 'text'});
  }
}
