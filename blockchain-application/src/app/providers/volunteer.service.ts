import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {Task, Volunteer} from 'app/model/at.jku.cis';

@Injectable()
export class TaskService {

  private NAMESPACE = 'Volunteer';

  constructor(private dataService: DataService<Volunteer>) {
  };

  public getAll(): Observable<Volunteer[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<Volunteer> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public addAsset(itemToAdd: any): Observable<Volunteer> {
    return this.dataService.add(this.NAMESPACE, itemToAdd);
  }

  public updateAsset(id: any, itemToUpdate: any): Observable<Volunteer> {
    return this.dataService.update(this.NAMESPACE, id, itemToUpdate);
  }

  public deleteAsset(id: any): Observable<Volunteer> {
    return Observable.throw(new Error('Not implemented'));
  }

}
