import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Volunteer} from './volunteer';
import {VolunteerProfile} from './volunteer-profile';
import {isNullOrUndefined} from 'util';
import {TaskEntry} from './task-entry';

@Injectable()
export class VolunteerRepositoryService {

  private apiUrl = 'http://localhost:3000/repository';

  constructor(private http: HttpClient) {
  }

  findByVolunteer(volunteer: Volunteer) {
    const observable = new Observable(subscriber => {

      const successFunction = (volunteerProfile: VolunteerProfile) => {
        subscriber.next(volunteerProfile);
        subscriber.complete();
      };

      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.http.get(this.apiUrl)
        .toPromise()
        .then((volunteerProfiles: VolunteerProfile[]) => {
          successFunction(volunteerProfiles.find((volunteerProfile: VolunteerProfile) => {
            return volunteerProfile.volunteer.username === volunteer.username;
          }));
        })
        .catch((error: any) => failureFunction(error));
    });
    return observable;
  }

  findTasksByVolunteer(volunteer: Volunteer) {
    const observable = new Observable(subscriber => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      const successFunction = (volunteerProfile: VolunteerProfile) => {
        if (isNullOrUndefined(volunteerProfile)) {
          subscriber.next([]);
        } else {
          subscriber.next(volunteerProfile.taskList);
        }
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((volunteerProfile: VolunteerProfile) => successFunction(volunteerProfile))
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  synchronizeTask(volunteer: Volunteer, taskEntry: TaskEntry) {
    const observable = new Observable(subscriber => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((volunteerProfile: VolunteerProfile) => {
          volunteerProfile.taskList.push(taskEntry);
          this.http.put(`${this.apiUrl}/${volunteerProfile.id}`, volunteerProfile)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((error: any) => failureFunction(error));
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  findCompetencesByVolunteer(volunteer: Volunteer) {
    const observable = new Observable(subscriber => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      const successFunction = (volunteerProfile: VolunteerProfile) => {
        if (isNullOrUndefined(volunteerProfile)) {
          subscriber.next([]);
        } else {
          subscriber.next(volunteerProfile.competenceList);
        }
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((volunteerProfile: VolunteerProfile) => successFunction(volunteerProfile))
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }
}
