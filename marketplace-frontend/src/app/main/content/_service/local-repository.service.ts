import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Volunteer } from '../_model/volunteer';
import { LocalRepository } from '../_model/local-repository';
import { isNullOrUndefined } from 'util';
import { ClassInstanceDTO } from '../_model/meta/Class';

@Injectable({
    providedIn: 'root'
})
export class LocalRepositoryService {

    private apiUrl = 'http://localhost:3000/repository';

    constructor(private http: HttpClient) {
    }

     async isConnected() {
            let isConnected;
            await this.http.get(this.apiUrl)
                .toPromise()
                .then(() => {
                    isConnected = true;
                })
                .catch((error: any) => {
                    isConnected = false;
               });

               return isConnected;
     
    }


    findByVolunteer(volunteer: Volunteer) {
        const observable = new Observable(subscriber => {

            const successFunction = (localRepository: LocalRepository) => {
                subscriber.next(localRepository);
                subscriber.complete();
            };

            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.http.get(this.apiUrl)
                .toPromise()
                .then((localRepositorys: LocalRepository[]) => {
                    successFunction(localRepositorys.find((localRepository: LocalRepository) => {
                        return localRepository.volunteer.username === volunteer.username;
                    }));
                })
                .catch((error: any) => failureFunction(error));
        });
        return observable;
    }

    findByVolunteerAndArcheType(volunteer: Volunteer) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            const successFunction = (localRepository: LocalRepository) => {
                if (isNullOrUndefined(localRepository)) {
                    subscriber.next([]);
                } else {
                    subscriber.next(localRepository.classInstances);
                    subscriber.complete();
                }
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((localRepository: LocalRepository) => successFunction(localRepository))
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }


    synchronizeClassInstance(volunteer: Volunteer, classInstance: ClassInstanceDTO) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((localRepository: LocalRepository) => {
                    localRepository.classInstances.push(classInstance);

                    this.http.put(`${this.apiUrl}/${localRepository.id}`, localRepository)
                        .toPromise()
                        .then(() => subscriber.complete())
                        .catch((error: any) => failureFunction(error));
                })
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }

    synchronizeClassInstances(volunteer: Volunteer, classInstances: ClassInstanceDTO[]) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((localRepository: LocalRepository) => {

                    localRepository.classInstances = [ ...localRepository.classInstances, ...classInstances];

                      this.http.put(`${this.apiUrl}/${localRepository.id}`, localRepository)
                      .toPromise()
                        .then(() => subscriber.complete())
                        .catch((error: any) => failureFunction(error));
                })
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }


    removeClassInstance(volunteer: Volunteer, classInstance: ClassInstanceDTO) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((localRepository: LocalRepository) => {
                    localRepository.classInstances.forEach((ci, index, object) => {
                        if (ci.id === classInstance.id) {
                            object.splice(index, 1);
                        }
                    });

                    this.http.put(`${this.apiUrl}/${localRepository.id}`, localRepository)
                        .toPromise()
                        .then(() => subscriber.complete())
                        .catch((error: any) => failureFunction(error));
                })
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }

    removeAllClassInstances(volunteer: Volunteer) {
        const observable = new Observable(subscriber => {
            const failureFunction = (error: any) => {
                subscriber.error(error);
                subscriber.complete();
            };

            this.findByVolunteer(volunteer)
                .toPromise()
                .then((localRepository: LocalRepository) => {
                    localRepository.classInstances = [];

                    this.http.put(`${this.apiUrl}/${localRepository.id}`, localRepository)
                        .toPromise()
                        .then(() => subscriber.complete())
                        .catch((error: any) => failureFunction(error));
                })
                .catch((error: any) => failureFunction(error));
        });

        return observable;
    }

}