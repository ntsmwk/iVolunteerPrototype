import { Injectable } from "@angular/core";
import { User } from "../_model/user";
import { Observable } from "rxjs";
import { LocalRepository } from "../_model/local-repository";
import { ClassInstance } from "../_model/meta/class";
import { Dropbox } from "dropbox";
import { LocalRepositoryService } from "./local-repository.service";

@Injectable({
  providedIn: "root",
})
export class LocalRepositoryDropboxService extends LocalRepositoryService {
  FILE_PATH: string = "/db.json";
  localRepositorys: LocalRepository[] = [];

  constructor() {
    super();
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
        if (volunteer.dropboxToken) {
          let dbx = new Dropbox({ accessToken: volunteer.dropboxToken });

          dbx.filesListFolder({ path: "" }).then((filesList) => {
            if (
              filesList.entries.findIndex(
                (entry) => entry.name === "db.json"
              ) == -1
            ) {
              // create and read
              dbx
                .filesUpload({
                  contents: JSON.stringify({ repository: [] }, null, 2),
                  path: this.FILE_PATH,
                  mode: { ".tag": "overwrite" },
                  autorename: false,
                  mute: false,
                })
                .then(() => {
                  dbx.filesDownload({ path: this.FILE_PATH }).then((file) => {
                    let reader = new FileReader();
                    let blob: Blob = (<any>file).fileBlob;

                    reader.addEventListener("loadend", () => {
                      this.localRepositorys = [];
                      if (this.isValidJson(<string>reader.result)) {
                        let parsedJSON = JSON.parse(<string>reader.result);
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
                        this.saveToDropbox(volunteer);
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
                    reader.readAsText(blob);
                  });
                })
                .catch((error) => {
                  console.error(error);
                  alert("Failed to save to dropbox");
                });
            } else {
              // only read
              dbx.filesDownload({ path: this.FILE_PATH }).then((file) => {
                let reader = new FileReader();
                let blob: Blob = (<any>file).fileBlob;

                reader.addEventListener("loadend", () => {
                  this.localRepositorys = [];
                  if (this.isValidJson(<string>reader.result)) {
                    let parsedJSON = JSON.parse(<string>reader.result);
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
                    this.saveToDropbox(volunteer);
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
                reader.readAsText(blob);
              });
            }
          });
        } else {
          failureFunction(null);
        }
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
            this.saveToDropbox(volunteer);
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

            this.saveToDropbox(volunteer);
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

            this.saveToDropbox(volunteer);
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

            this.saveToDropbox(volunteer);
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

            this.saveToDropbox(volunteer);
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

  private saveToDropbox(volunteer: User) {
    let dbx = new Dropbox({ accessToken: volunteer.dropboxToken });
    let content = { repository: this.localRepositorys };

    dbx
      .filesUpload({
        contents: JSON.stringify(content, null, 2),
        path: this.FILE_PATH,
        mode: { ".tag": "overwrite" },
        autorename: false,
        mute: false,
      })
      .then(() => {
        // console.error("successfully written to dropbox");
      })
      .catch((error) => {
        console.error(error);
        alert("Failed to save to dropbox");
      });
  }

  isValidJson(str) {
    try {
      JSON.parse(str);
    } catch (e) {
      return false;
    }
    return true;
  }

  isConnected(credentials: any) {
    throw new Error("Method not implemented.");
  }
}
