import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskInteraction} from '../task-interaction/task-interaction';
import {Observable} from '../../../../../../prototype/marketplace-client/node_modules/rxjs/Observable';

@Injectable()
export class RepositoryService {

  private apiUrl = 'http://localhost:3000';

  constructor(private http: HttpClient) {
  }

  getProfile() {
    return this.http.get([this.apiUrl, 'profile'].join('/'));
  }

  getTasks() {
    return this.http.get([this.apiUrl, 'tasks'].join('/'));
  }

  importTask(taskInteraction: TaskInteraction) {
    const localTask = {
      'id': taskInteraction.id,
      'taskId': taskInteraction.task.id,
      'participantId': taskInteraction.participant.id,
      'timestamp': taskInteraction.timestamp
    };

    const observable = new Observable(subscriber => {
      const saveFunction = () => {
        this.http.post([this.apiUrl, 'tasks'].join('/'), localTask)
          .toPromise()
          .then(() => subscriber.complete())
          .catch((reason: any) => subscriber.error(reason));
      };

      this.http.delete([this.apiUrl, 'tasks', localTask.id].join('/'))
        .toPromise()
        .then(() => saveFunction())
        .catch(() => saveFunction());
    });
    return observable;
  }
}
