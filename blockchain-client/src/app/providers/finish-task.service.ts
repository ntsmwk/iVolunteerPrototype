import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {FinishTask} from '../model/at.jku.cis';

@Injectable()
export class FinishTaskService {

  private NAMESPACE = 'finishTask';

  constructor(private dataService: DataService<FinishTask>) {
  };

  public getAll(): Observable<FinishTask[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<FinishTask> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public addAsset(itemToAdd: FinishTask): Observable<FinishTask> {
    return this.dataService.add(this.NAMESPACE, itemToAdd);
  }
}
