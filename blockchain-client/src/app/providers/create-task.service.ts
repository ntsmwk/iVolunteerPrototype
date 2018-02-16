import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {CreateTask} from 'app/model/at.jku.cis';

@Injectable()
export class CreateTaskService {

  private NAMESPACE = 'createTask';

  constructor(private dataService: DataService<CreateTask>) {
  };

  public getAll(): Observable<CreateTask[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<CreateTask> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public addAsset(itemToAdd: CreateTask): Observable<CreateTask> {
    return this.dataService.add(this.NAMESPACE, itemToAdd);
  }
}
