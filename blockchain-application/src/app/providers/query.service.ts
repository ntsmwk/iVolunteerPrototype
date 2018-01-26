import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {Task} from 'app/model/at.jku.cis';

@Injectable()
export class QueryService {

  private NAMESPACE = 'queries';

  constructor(private dataService: DataService<Task>) {
  };


}
