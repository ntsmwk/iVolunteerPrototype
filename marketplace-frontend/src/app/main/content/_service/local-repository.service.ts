import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { LocalRepository } from "../_model/local-repository";
import { isNullOrUndefined } from "util";
import { ClassInstance } from "../_model/meta/class";
import { User } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class LocalRepositoryService {
  private apiUrl = "http://localhost:3000/repository";
  // private apiUrl = "http://140.78.92.57:3000/repository";

  constructor(private http: HttpClient) { }

  findByVolunteer(volunteer: User) {
    const observable = new Observable((subscriber) => {
      const successFunction = (localRepository: LocalRepository) => {
        subscriber.next(localRepository);
        subscriber.complete();
      };

      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.http
        .get(this.apiUrl)
        .toPromise()
        .then((localRepositorys: LocalRepository[]) => {
          if (localRepositorys.findIndex((l) => l.id === volunteer.id) === -1) {
            let newRepo = new LocalRepository(volunteer.id, volunteer.username);
            this.http.post(this.apiUrl, newRepo).toPromise();
            successFunction(newRepo);
          } else {
            successFunction(
              localRepositorys.find((localRepository: LocalRepository) => {
                return localRepository.id === volunteer.id;
              })
            );
          }
        })
        .catch((error: any) => failureFunction(error));
    });
    return observable;
  }

  findClassInstancesByVolunteer(volunteer: User) {
    const observable = new Observable((subscriber) => {
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
        .then((localRepository: LocalRepository) =>
          successFunction(localRepository)
        )
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  synchronizeSingleClassInstance(
    volunteer: User,
    classInstance: ClassInstance
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          // TODO: prevent double insertion, currently handled in calling method
          localRepository.classInstances.push(classInstance);

          this.http
            .put(`${this.apiUrl}/${localRepository.id}`, localRepository)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((error: any) => failureFunction(error));

          subscriber.next(localRepository.classInstances);
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  getSingleClassInstance(volunteer: User, classInstanceId: string) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          let classInstance = localRepository.classInstances.find((ci) => {
            return ci.id === classInstanceId;
          });
          subscriber.next(classInstance);
          subscriber.complete();
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  synchronizeClassInstances(volunteer: User, classInstances: ClassInstance[]) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          // TODO: prevent double insertion, currently handled in calling method
          localRepository.classInstances = [
            ...localRepository.classInstances,
            ...classInstances,
          ];

          this.http
            .put(`${this.apiUrl}/${localRepository.id}`, localRepository)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((error: any) => failureFunction(error));

          subscriber.next(localRepository.classInstances);
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  removeSingleClassInstance(volunteer: User, classInstanceId: string) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          localRepository.classInstances.forEach((ci, index, object) => {
            if (ci.id === classInstanceId) {
              object.splice(index, 1);
            }
          });

          this.http
            .put(`${this.apiUrl}/${localRepository.id}`, localRepository)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((error: any) => failureFunction(error));

          subscriber.next(localRepository.classInstances);
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  removeClassInstances(volunteer: User, classInstanceIds: string[]) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          localRepository.classInstances = localRepository.classInstances.filter(
            (c) => classInstanceIds.indexOf(c.id) < 0
          );

          this.http
            .put(`${this.apiUrl}/${localRepository.id}`, localRepository)
            .toPromise()
            .then(() => subscriber.complete())
            .catch((error: any) => failureFunction(error));

          subscriber.next(localRepository.classInstances);
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  // async isConnected(volunteer: User) {
  //   let isConnected;

  //   let localRepos = <LocalRepository[]>await this.http
  //     .get(this.apiUrl)
  //     .toPromise()
  //     .catch(() => (isConnected = false));

  //   if (localRepos) {
  //     isConnected = true;
  //     if (localRepos.findIndex((l) => l.id === volunteer.id) === -1) {
  //       let newRepo = new LocalRepository(volunteer.id, volunteer.username);
  //       this.http
  //         .post(this.apiUrl, newRepo)
  //         .toPromise()
  //         .catch((e) => console.log(e));
  //     }
  //   }

  //   return isConnected;
  // }
}
