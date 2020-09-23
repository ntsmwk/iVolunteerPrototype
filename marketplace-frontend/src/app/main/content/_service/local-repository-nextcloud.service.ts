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
  FILE_PATH: string = "/Apps/iVolunteer/";
  FILE_NAME: string = "db.json";
  FULL_FILE_NAME: string = this.FILE_PATH + this.FILE_NAME;

  localRepositorys: LocalRepository[] = [];

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
    const observable = new Observable((subscriber) => {
      const successFunction = (localRepository: LocalRepository) => {
        subscriber.next(localRepository);
        subscriber.complete();
      };

      const failureFunction = (error: any) => {
        subscriber.error(error);
        subscriber.complete();
      };

      try {
        let nextcloud = createClient(volunteer.nextcloudCredentials.domain, {
          username: volunteer.nextcloudCredentials.username,
          password: volunteer.nextcloudCredentials.password,
        });

        nextcloud
          .getDirectoryContents(this.FILE_PATH)
          .then(async (filesList) => {
            if (
              filesList.findIndex(
                (entry) => entry.basename === this.FILE_NAME
              ) == -1
            ) {
              // create and read
              nextcloud
                .putFileContents(
                  this.FULL_FILE_NAME,
                  JSON.stringify({ repository: [] }, null, 2),
                  {
                    overwrite: true,
                  }
                )
                .then(async () => {
                  let parsedJSON = await nextcloud.getFileContents(
                    this.FULL_FILE_NAME,
                    {
                      format: "text",
                    }
                  );

                  this.localRepositorys = [];
                  parsedJSON.repository.forEach((lr: LocalRepository) => {
                    this.localRepositorys.push(lr);
                  });

                  if (
                    this.localRepositorys.findIndex(
                      (l) => l.id === volunteer.id
                    ) === -1
                  ) {
                    let newRepo = new LocalRepository(
                      volunteer.id,
                      volunteer.username
                    );

                    this.localRepositorys.push(newRepo);
                    this.saveToNextcloud(volunteer);
                    successFunction(newRepo);
                  } else {
                    successFunction(
                      this.localRepositorys.find(
                        (localRepository: LocalRepository) => {
                          return localRepository.id === volunteer.id;
                        }
                      )
                    );
                  }
                });
            } else {
              // only read
              let parsedJSON = await nextcloud.getFileContents(
                this.FULL_FILE_NAME,
                {
                  format: "text",
                }
              );

              this.localRepositorys = [];
              parsedJSON.repository.forEach((lr: LocalRepository) => {
                this.localRepositorys.push(lr);
              });

              if (
                this.localRepositorys.filter((lr) => lr.id == volunteer.id)
                  .length == 0
              ) {
                let newRepo = new LocalRepository(
                  volunteer.id,
                  volunteer.username
                );

                this.localRepositorys.push(newRepo);
                this.saveToNextcloud(volunteer);
                successFunction(newRepo);
              } else {
                successFunction(
                  this.localRepositorys.find(
                    (localRepository: LocalRepository) => {
                      return localRepository.id === volunteer.id;
                    }
                  )
                );
              }
            }
          });
      } catch (error) {
        failureFunction(error);
      }
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

            this.localRepositorys.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositorys[index] = localRepository;
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

            this.localRepositorys.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositorys[index] = localRepository;
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

            this.localRepositorys.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositorys[index] = localRepository;
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

            this.localRepositorys.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositorys[index] = localRepository;
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

            this.localRepositorys.forEach((lr, index) => {
              if (lr.id == localRepository.id) {
                this.localRepositorys[index] = localRepository;
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

    let content = { repository: this.localRepositorys };

    await nextcloud.putFileContents(
      this.FULL_FILE_NAME,
      JSON.stringify(content, null, 2),
      {
        overwrite: true,
      }
    );
  }
}
