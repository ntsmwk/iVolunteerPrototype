import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Volunteer } from '../_model/volunteer';
import { VolunteerProfile } from '../_model/volunteer-profile';
import { TaskEntry } from '../_model/task-entry';
import { CompetenceEntry } from '../_model/competence-entry';
import { isNullOrUndefined } from 'util';

@Injectable({
    providedIn: 'root'
})
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

    synchronizeCompetence(volunteer: Volunteer, competenceEntry: CompetenceEntry) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((volunteerProfile: VolunteerProfile) => {
                    volunteerProfile.competenceList.push(competenceEntry);
                    this.http.put(`${this.apiUrl}/${volunteerProfile.id}`, volunteerProfile)
                        .toPromise()
                        .then(() => subscriber.complete())
                        .catch((error: any) => failureFunction(error));
                })
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }
}