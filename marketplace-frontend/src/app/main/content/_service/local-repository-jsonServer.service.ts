import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { LocalRepository } from "../_model/local-repository";
import { isNullOrUndefined } from "util";
import { ClassInstance } from "../_model/meta/class";
import { LocalRepositoryService } from "./local-repository.service";
import { environment } from "environments/environment";
import { UserInfo } from "../_model/userInfo";

@Injectable({
  providedIn: "root",
})
export class LocalRepositoryJsonServerService extends LocalRepositoryService {
  private apiUrl = environment.JSON_SERVER_URL;

  constructor(private http: HttpClient) {
    super();
  }

  public async isConnected() {
    try {
      await this.http.get(this.apiUrl).toPromise();
      return true;
    } catch (error) {
      return false;
    }
  }

  private findByVolunteer(volunteerInfo: UserInfo) {
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
          if (
            localRepositorys.findIndex((l) => l.id === volunteerInfo.id) === -1
          ) {
            let newRepo = new LocalRepository(
              volunteerInfo.id,
              volunteerInfo.username
            );
            this.http.post(this.apiUrl, newRepo).toPromise();
            successFunction(newRepo);
          } else {
            successFunction(
              localRepositorys.find((localRepository: LocalRepository) => {
                return localRepository.id === volunteerInfo.id;
              })
            );
          }
        })
        .catch((error: any) => {
          failureFunction(error);
        });
    });
    return observable;
  }

  public findClassInstancesByVolunteer(volunteerInfo: UserInfo) {
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

      this.findByVolunteer(volunteerInfo)
        .toPromise()
        .then((localRepository: LocalRepository) =>
          successFunction(localRepository)
        )
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  public synchronizeSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstance: ClassInstance
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
        .toPromise()
        .then((localRepository: LocalRepository) => {
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

  public getSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstanceId: string
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
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

  public synchronizeClassInstances(
    volunteerInfo: UserInfo,
    classInstances: ClassInstance[]
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
        .toPromise()
        .then((localRepository: LocalRepository) => {
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

  public overrideClassInstances(
    volunteerInfo: UserInfo,
    classInstances: ClassInstance[]
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          localRepository.classInstances = classInstances;

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

  public removeSingleClassInstance(
    volunteerInfo: UserInfo,
    classInstanceId: string
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
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

  public removeClassInstances(
    volunteerInfo: UserInfo,
    classInstanceIds: string[]
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteerInfo)
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
}
