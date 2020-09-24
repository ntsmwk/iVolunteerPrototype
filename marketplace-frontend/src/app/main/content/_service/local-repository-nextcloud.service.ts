import { Injectable } from "@angular/core";
import { User } from "../_model/user";
import { Observable } from "rxjs";
import { LocalRepository } from "../_model/local-repository";
import { ClassInstance } from "../_model/meta/class";
import { createClient } from "webdav/web";
import { NextcloudCredentials } from "../_model/nextcloud-credentials";
import { LocalRepositoryService } from "./local-repository.service";

@Injectable({
  providedIn: "root",
})
export class LocalRepositoryNextcloudService extends LocalRepositoryService {
  FILE_PATH1: string = "/Apps";
  FILE_PATH2: string = "/Apps/iVolunteer/";
  FILE_NAME: string = "db.json";
  FULL_FILE_NAME: string = this.FILE_PATH2 + this.FILE_NAME;

  localRepositories: LocalRepository[] = [];

  constructor() {
    super();
  }

  public async isConnected(credentials: NextcloudCredentials) {
    if (
      credentials == null ||
      credentials.domain == null ||
      credentials.username == null ||
      credentials.password == null
    ) {
      return false;
    }

    let nextcloud = createClient(credentials.domain, {
      username: credentials.username,
      password: credentials.password,
    });

    try {
      await nextcloud.getDirectoryContents("/");
      return true;
    } catch (error) {
      return false;
    }
  }

  private findByVolunteer(volunteer: User) {
    let nextcloud;

    const observable = new Observable((subscriber) => {
      const successFunction = (localRepository: LocalRepository) => {
        subscriber.next(localRepository);
        subscriber.complete();
      };

      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      (async () => {
        try {
          nextcloud = createClient(volunteer.nextcloudCredentials.domain, {
            username: volunteer.nextcloudCredentials.username,
            password: volunteer.nextcloudCredentials.password,
          });
        } catch (error) {
          console.error("nextcloud client creation failed");
          failureFunction(error);
        }

        try {
          await nextcloud.getDirectoryContents(this.FILE_PATH2);
        } catch (error) {
          // create path and db.json
          await nextcloud.createDirectory(this.FILE_PATH1);
          await nextcloud.createDirectory(this.FILE_PATH2);

          let newRepo = new LocalRepository(volunteer.id, volunteer.username);
          this.localRepositories.push(newRepo);
          let content = { repository: this.localRepositories };

          await nextcloud.putFileContents(
            this.FULL_FILE_NAME,
            JSON.stringify(content, null, 2)
          );

          successFunction(newRepo);
        }

        try {
          let parsedJSON = await nextcloud.getFileContents(
            this.FULL_FILE_NAME,
            {
              format: "text",
            }
          );

          this.localRepositories = [];
          parsedJSON.repository.forEach((lr: LocalRepository) => {
            this.localRepositories.push(lr);
          });

          let repository = this.localRepositories.find(
            (localRepository: LocalRepository) => {
              return localRepository.id === volunteer.id;
            }
          );

          if (repository) {
            successFunction(repository);
          } else {
            let newRepo = new LocalRepository(volunteer.id, volunteer.username);
            this.localRepositories.push(newRepo);
            this.saveToNextcloud(volunteer);
            successFunction(newRepo);
          }
        } catch (error) {
          failureFunction(error);
        }
      })().then(null, subscriber.error);
    });
    return observable;
  }

  public findClassInstancesByVolunteer(volunteer: User) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      const successFunction = (localRepository: LocalRepository) => {
        if (!localRepository) {
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

  public synchronizeSingleClassInstance(
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
          try {
            localRepository.classInstances.push(classInstance);

            this.localRepositories.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositories[index] = localRepository;
              }
            });
            this.saveToNextcloud(volunteer);
            subscriber.next(localRepository.classInstances);
            subscriber.complete();
          } catch (error) {
            failureFunction(error);
          }
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  public getSingleClassInstance(volunteer: User, classInstanceId: string) {
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

  public synchronizeClassInstances(
    volunteer: User,
    classInstances: ClassInstance[]
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          try {
            localRepository.classInstances = [
              ...localRepository.classInstances,
              ...classInstances,
            ];

            this.localRepositories.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositories[index] = localRepository;
              }
            });

            this.saveToNextcloud(volunteer);
            subscriber.next(localRepository.classInstances);
            subscriber.complete();
          } catch (error) {
            failureFunction(error);
          }
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  public overrideClassInstances(
    volunteer: User,
    classInstances: ClassInstance[]
  ) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          try {
            localRepository.classInstances = classInstances;

            this.localRepositories.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositories[index] = localRepository;
              }
            });

            this.saveToNextcloud(volunteer);
            subscriber.next(localRepository.classInstances);
            subscriber.complete();
          } catch (error) {
            failureFunction(error);
          }
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  public removeSingleClassInstance(volunteer: User, classInstanceId: string) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          try {
            localRepository.classInstances.forEach((ci, index, object) => {
              if (ci.id === classInstanceId) {
                object.splice(index, 1);
              }
            });

            this.localRepositories.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositories[index] = localRepository;
              }
            });

            this.saveToNextcloud(volunteer);
            subscriber.next(localRepository.classInstances);
            subscriber.complete();
          } catch (error) {
            failureFunction(error);
          }
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  public removeClassInstances(volunteer: User, classInstanceIds: string[]) {
    const observable = new Observable((subscriber) => {
      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      this.findByVolunteer(volunteer)
        .toPromise()
        .then((localRepository: LocalRepository) => {
          try {
            localRepository.classInstances = localRepository.classInstances.filter(
              (c) => classInstanceIds.indexOf(c.id) < 0
            );

            this.localRepositories.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositories[index] = localRepository;
              }
            });

            this.saveToNextcloud(volunteer);
            subscriber.next(localRepository.classInstances);
            subscriber.complete();
          } catch (error) {
            failureFunction(error);
          }
        })
        .catch((error: any) => failureFunction(error));
    });

    return observable;
  }

  private async saveToNextcloud(volunteer: User) {
    let nextcloud = createClient(volunteer.nextcloudCredentials.domain, {
      username: volunteer.nextcloudCredentials.username,
      password: volunteer.nextcloudCredentials.password,
    });

    let content = { repository: this.localRepositories };

    await nextcloud.putFileContents(
      this.FULL_FILE_NAME,
      JSON.stringify(content, null, 2),
      {
        overwrite: true,
      }
    );
  }
}
