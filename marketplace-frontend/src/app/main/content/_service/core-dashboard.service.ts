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

  findById(dashboardId: string) {
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

      this.http.get(`/core/dashboard/${dashboardId}`).toPromise()
        .then((dashboard: Dashboard) => successFunction(dashboard))
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  findByParticipant(participantId) {
    return this.http.get(`/core/dashboard?participantId${participantId}`);
  }

  save(dashboard: Dashboard) {
    if (isNullOrUndefined(dashboard.id)) {
      return this.http.post(`/core/dashboard`, dashboard);
    }
    return this.http.put(`/core/dashboard/${dashboard.id}`, dashboard);
  }

  remove(dashboard: Dashboard) {
    return this.http.delete(`/core/dashboard/${dashboard.id}`);
  }
}
