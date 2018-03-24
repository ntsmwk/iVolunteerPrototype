import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../task/task';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';
import {TaskEntry} from './task-entry';

@Injectable()
export class VolunteerRepositoryService {

  private apiUrl = 'http://localhost:3000';

  constructor(private http: HttpClient) {
  }

  getProfile() {
    return this.http.get([this.apiUrl, 'profile'].join('/'));
  }

  findAllTasks() {
    return this.http.get([this.apiUrl, 'tasks'].join('/'));
  }

  isTaskImported(task: Task) {
    const observable = new Observable(subscriber => {
      const successFunction = (value: boolean) => {
        subscriber.next(value);
        subscriber.complete();
      };
      const failureFunction = (reason) => {
        subscriber.error(reason);
        subscriber.complete();
      };

      this.http.get(this.apiUrl + '/tasks?taskId=' + task.id).toPromise()
        .then((values: any[]) => {
          if (isNullOrUndefined(values) || values.length !== 1) {
            successFunction(false);
          } else {
            successFunction(true);
          }
        })
        .catch((reason) => failureFunction(reason));
    });

    return observable;
  }

  saveTask(taskEntry: TaskEntry) {
    const observable = new Observable(subscriber => {
      const saveFunction = () => {
        this.http.post([this.apiUrl, 'tasks'].join('/'), taskEntry)
          .toPromise()
          .then(() => subscriber.complete())
          .catch((reason: any) => {
            subscriber.error(reason);
            subscriber.complete();
          });
      };

      this.http.delete([this.apiUrl, 'tasks', taskEntry.id].join('/'))
        .toPromise()
        .then(() => saveFunction())
        .catch(() => saveFunction());
    });
    return observable;
  }
}
