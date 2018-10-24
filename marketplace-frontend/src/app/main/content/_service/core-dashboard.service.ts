import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Dashboard} from '../_model/dashboard';
import {isNullOrUndefined} from 'util';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoreDashboardService {

  constructor(private http: HttpClient) {
  }

  findCurrent() {
    const observable = new Observable(subscriber => {
      const failureFunction = (error: HttpErrorResponse) => {
        if (error.status === 404) {
          successFunction(undefined);
        } else {
          subscriber.error(error);
          subscriber.complete();
        }
      };

      const successFunction = (dashboard: Dashboard) => {
        subscriber.next(dashboard);
        subscriber.complete();
      };

      this.http.get(`/core/dashboard/current`).toPromise()
        .then((dashboard: Dashboard) => successFunction(dashboard))
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  save(dashboard: Dashboard) {
    if (isNullOrUndefined(dashboard.id)) {
      return this.http.post(`/core/dashboard`, dashboard);
    }
    return this.http.put(`/core/dashboard/${dashboard.id}/dashlet`, dashboard.dashlets);
  }

}
