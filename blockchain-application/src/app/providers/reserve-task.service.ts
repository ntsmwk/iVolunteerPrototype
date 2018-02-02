import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {ReserveTask} from 'app/model/at.jku.cis';

@Injectable()
export class ReserveTaskService {

  private NAMESPACE = 'reserveTask';

  constructor(private dataService: DataService<ReserveTask>) {
  };

  public getAll(): Observable<ReserveTask[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<ReserveTask> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public addAsset(itemToAdd: ReserveTask): Observable<ReserveTask> {
    return this.dataService.add(this.NAMESPACE, itemToAdd);
  }
}
