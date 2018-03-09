import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskInteraction} from '../task-interaction/task-interaction';

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
      'task': {
        'id': taskInteraction.task.id
      },
      'participant': {
        'id': taskInteraction.participant.id
      },
      'timestamp': taskInteraction.timestamp
    };

    /*const observable = new Observable(subscriber => {
      this.http.delete([this.apiUrl, 'tasks', localTask.id].join('/'))
        .toPromise()
        .then(() => {

        })
        .catch(() => {
          this.http.post([this.apiUrl, 'tasks'].join('/'), localTask)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((reason: any) => subscriber.error(reason));
        });
    });*/

    return this.http.post([this.apiUrl, 'tasks'].join('/'), localTask);
  }

}
