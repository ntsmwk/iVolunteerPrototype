import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Volunteer} from './volunteer';
import {VolunteerProfile} from './volunteer-profile';

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

}
