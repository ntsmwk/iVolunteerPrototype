import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import { DropboxUtils } from "./dropbox-utils";
import { Dropbox } from "dropbox";
import { LoginService } from "app/main/content/_service/login.service";
import { User, LocalRepositoryLocation } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { CoreUserService } from "app/main/content/_service/core-user.serivce";
import { LocalRepositoryJsonServerService } from "app/main/content/_service/local-repository-jsonServer.service";
import { LocalRepositoryDropboxService } from "app/main/content/_service/local-repository-dropbox.service";
import { ClassInstance } from "app/main/content/_model/meta/class";

@Component({
  selector: "app-local-repository-location-switch",
  templateUrl: "./local-repository-location-switch.component.html",
  styleUrls: ["./local-repository-location-switch.component.scss"],
})
export class LocalRepositoryLocationSwitchComponent implements OnInit {
  CLIENT_ID: string = "ky4bainncqhjjn8";
  FILE_NAME: string = "db.json";
  authUrl: string;
  isAuthenticated: boolean = false;

  globalInfo: GlobalInfo;
  user: User;
  isLocalRepoDropbox: boolean;
  isJsonServerConnected: boolean = false;

  isLoaded: boolean = false;

  constructor(
    private location: Location,
    private loginService: LoginService,
    private coreUserService: CoreUserService,
    private localRepoJsonServerService: LocalRepositoryJsonServerService,
    private localRepoDropboxService: LocalRepositoryDropboxService
  ) {}

  async ngOnInit() {
    var dbx = new Dropbox({ clientId: this.CLIENT_ID });
    this.authUrl = dbx.getAuthenticationUrl(
      "http://localhost:4200/main/profile"
    );

    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.user = this.globalInfo.user;

    if (this.user.dropboxToken && this.user.dropboxToken !== "undefined") {
      this.isAuthenticated = true;
    } else {
      let dropboxToken = DropboxUtils.getAccessTokenFromUrl();
      this.isAuthenticated = dropboxToken && dropboxToken !== "undefined";

      if (this.isAuthenticated && dropboxToken != this.user.dropboxToken) {
        this.user.dropboxToken = dropboxToken;
        await this.coreUserService.updateUser(this.user, true).toPromise();
        this.updateGlobalInfo();
      }
    }

    this.isLoaded = true;

    this.isJsonServerConnected = await this.localRepoJsonServerService.isConnected();
  }

  async removeToken() {
    try {
      if (this.isJsonServerConnected) {
        if (
          this.user.localRepositoryLocation == LocalRepositoryLocation.DROPBOX
        ) {
          this.localRepositoryLocationChange(LocalRepositoryLocation.LOCAL);
          this.user.localRepositoryLocation = LocalRepositoryLocation.LOCAL;
          await this.coreUserService.updateUser(this.user, true).toPromise();
          this.updateGlobalInfo();
        }

        this.user.dropboxToken = null;
        await this.coreUserService.updateUser(this.user, true).toPromise();
        this.updateGlobalInfo();

        this.location.replaceState("/main/profile");

        this.isAuthenticated = false;
      } else {
        alert(
          "Löschen der Dropbox Verbindung nicht möglich, da keine Verbindung zum lokalen Speicherort (json-server) vorhanden ist!"
        );
      }
    } catch (error) {
      console.error(error);
    }
  }

  async localRepositoryLocationChange(newLocation) {
    if (this.user.localRepositoryLocation != newLocation) {
      try {
        if (this.isJsonServerConnected) {
          if (newLocation == LocalRepositoryLocation.DROPBOX) {
            // jsonServer -> dropbox

            let classInstances = <ClassInstance[]>(
              await this.localRepoJsonServerService
                .findClassInstancesByVolunteer(this.user)
                .toPromise()
            );

            this.localRepoDropboxService
              .overrideClassInstances(this.user, classInstances)
              .toPromise();
          } else {
            //  dropbox -> jsonServer

            let classInstances = <ClassInstance[]>(
              await this.localRepoDropboxService
                .findClassInstancesByVolunteer(this.user)
                .toPromise()
            );

            this.localRepoJsonServerService
              .overrideClassInstances(this.user, classInstances)
              .toPromise();
          }

          this.user.localRepositoryLocation = newLocation;
          await this.coreUserService.updateUser(this.user, true).toPromise();
          this.updateGlobalInfo();
        } else {
          // TODO Philipp: revert radio button...
          alert(
            "Wechseln des Speicherorts nicht möglich, da keine Verbindung zum lokalen Speicherort (json-server) vorhanden ist!"
          );
        }
      } catch (error) {
        console.error(error);
      }
    }
  }

  getChecked(localRepositoryLocation) {
    return this.user.localRepositoryLocation == localRepositoryLocation
      ? true
      : false;
  }

  updateGlobalInfo() {
    this.loginService.generateGlobalInfo(
      this.globalInfo.userRole,
      this.globalInfo.tenants.map((t) => t.id)
    );
  }
}
