import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {CreateTask} from 'app/model/at.jku.cis';
import {AssignTask, Organisation, Task} from '../model/at.jku.cis';

@Injectable()
export class AssignTaskService {

  private NAMESPACE = 'assignTask';

  constructor(private dataService: DataService<AssignTask>) {
  };

  public getAll(): Observable<AssignTask[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<AssignTask> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public addAsset(itemToAdd: AssignTask): Observable<AssignTask> {
    return this.dataService.add(this.NAMESPACE, itemToAdd);
  }
}
