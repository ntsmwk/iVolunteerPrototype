import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassInstance } from 'app/main/content/_model/configurator/class';
import { ResponseService } from '../../../response.service';
import { isNullOrUndefined } from 'util';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClassInstanceService {
  constructor(private http: HttpClient,
    private responseService: ResponseService) { }

  createNewClassInstances(classInstances: ClassInstance[], redirectUrl: string) {

    if (isNullOrUndefined(classInstances) || classInstances.length <= 0) {
      return new Promise(() => null);
    }

    const classInstance = classInstances.pop();
    return this.responseService.sendClassInstanceConfiguratorResponse(redirectUrl, classInstance).toPromise().then(() => {
      return classInstance;
    });
  }


}
