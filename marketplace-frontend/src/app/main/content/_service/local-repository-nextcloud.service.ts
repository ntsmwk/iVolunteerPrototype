import { Injectable } from "@angular/core";
import { User } from "../_model/user";
import { Observable } from "rxjs";
import { LocalRepository } from "../_model/local-repository";
import { ClassInstance } from "../_model/meta/class";
import { createClient } from "webdav/web";

@Injectable({
  providedIn: "root",
})
export class LocalRepositoryNextcloudService {
  FILE_PATH: string = "/Apps/iVolunteer/";
  FILE_NAME: string = "db.json";
  FULL_FILE_NAME: string = this.FILE_PATH + this.FILE_NAME;

  NEXTCLOUD_DOMAIN: string =
    "http://philstar.ddns.net:31415/remote.php/dav/files/Philipp/";
  NEXTCLOUD_USERNAME: string = "Philipp";
  NEXTCLOUD_PASSWORD: string = "JcsBS-nTGXj-sG76N-fskTd-KeLdQ";

  localRepositorys: LocalRepository[] = [];

  constructor() {}

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
        let nextcloud = createClient(this.NEXTCLOUD_DOMAIN, {
          username: this.NEXTCLOUD_USERNAME,
          password: this.NEXTCLOUD_PASSWORD,
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
                  let result = await nextcloud.getFileContents(
                    this.FULL_FILE_NAME,
                    {
                      format: "text",
                    }
                  );

                  this.localRepositorys = [];
                  if (this.isValidJson(<string>result)) {
                    let parsedJSON = JSON.parse(<string>result);
                    parsedJSON.repository.forEach((lr: LocalRepository) => {
                      this.localRepositorys.push(lr);
                    });
                  }

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
              let result = await nextcloud.getFileContents(
                this.FULL_FILE_NAME,
                {
                  format: "text",
                }
              );

              this.localRepositorys = [];
              console.error(result);
              // TODO Philipp: PROBLEM result is not valid json
              if (this.isValidJson(result)) {
                let parsedJSON = JSON.parse(<string>result);

                parsedJSON.repository.forEach((lr: LocalRepository) => {
                  this.localRepositorys.push(lr);
                });
              }

              if (
                this.localRepositorys.filter((lr) => lr.id == volunteer.id)
                  .length == 0
              ) {
                console.error("newRepo");
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
    console.error("in saveToNextcloud");
    let nextcloud = createClient(this.NEXTCLOUD_DOMAIN, {
      username: this.NEXTCLOUD_USERNAME,
      password: this.NEXTCLOUD_PASSWORD,
    });

    let content = { repository: this.localRepositorys };

    await nextcloud.putFileContents(
      this.FULL_FILE_NAME,
      JSON.stringify(content, null, 2),
      {
        overwrite: true,
      },
      {
        onUploadProgress: (progress) => {
          console.error(progress);
        },
      }
    );
  }

  isValidJson(str) {
    try {
      JSON.parse(str);
    } catch (e) {
      console.error(e);

      return false;
    }
    return true;
  }
}
