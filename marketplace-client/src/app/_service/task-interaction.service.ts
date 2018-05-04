import {Injectable} from '@angular/core';
import {Task} from '../_model/task';
import {HttpClient} from '@angular/common/http';
import {Participant} from '../_model/participant';

@Injectable()
export class TaskInteractionService {

  constructor(private http: HttpClient) {
  }

  findByTask(task: Task) {
    return this.http.get('/marketplace/task/' + task.id + '/interaction');
  }

  findFinishedByTask(task: Task) {
    return this.http.get('/marketplace/task/' + task.id + '/interaction?operation=FINISHED');
  }

  findReservedVolunteersByTaskId(task: Task) {
    return this.http.get(`/marketplace/task/${task.id}/participant?operation=RESERVED`);
  }

  findAssignedVolunteersByTaskId(task: Task) {
    return this.http.get(`/marketplace/task/${task.id}/participant?operation=ASSIGNED`);
  }

  getLatestTaskOperation(task: Task, participant: Participant) {
    return this.http.get(`/marketplace/task/${task.id}/participant/${participant.id}`);
  }



  unreserve(task: Task) {
    return this.http.post(`/marketplace/task/${task.id}/unreserve`, {});
  }

  assign(task: Task, participant: Participant) {
    return this.http.post(`/marketplace/task/${task.id}/asssign`, []);
  }

  unassign(task: Task, participant: Participant) {
    return this.http.post(`/marketplace/task/${task.id}/unasssign`, []);
  }
}
