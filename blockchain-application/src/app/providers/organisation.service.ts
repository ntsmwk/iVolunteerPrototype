import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {Organisation, Volunteer} from 'app/model/at.jku.cis';

@Injectable()
export class OrganisationService {

  private NAMESPACE = 'Organisation';

  constructor(private dataService: DataService<Organisation>) {
  };

  public getAll(): Observable<Organisation[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<Organisation> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public updateAsset(id: any, itemToUpdate: Organisation): Observable<Organisation> {
    return this.dataService.update(this.NAMESPACE, id, itemToUpdate);
  }
}
