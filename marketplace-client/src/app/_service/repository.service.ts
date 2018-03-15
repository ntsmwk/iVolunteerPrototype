import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../task/task';
import {TaskInteraction} from '../task-interaction/task-interaction';
import {Observable} from 'rxjs/Observable';
import {isNullOrUndefined} from 'util';

@Injectable()
export class RepositoryService {

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

  saveTask(taskInteraction: TaskInteraction) {
    const localTask = {
      'id': taskInteraction.id,
      'task': {'id': taskInteraction.task.id},
      'participant': {'id': taskInteraction.participant.id},
      'timestamp': taskInteraction.timestamp
    };

    const observable = new Observable(subscriber => {
      const saveFunction = () => {
        this.http.post([this.apiUrl, 'tasks'].join('/'), localTask)
          .toPromise()
          .then(() => subscriber.complete())
          .catch((reason: any) => {
            subscriber.error(reason);
            subscriber.complete();
          });
      };

      this.http.delete([this.apiUrl, 'tasks', localTask.id].join('/'))
        .toPromise()
        .then(() => saveFunction())
        .catch(() => saveFunction());
    });
    return observable;
  }
}
