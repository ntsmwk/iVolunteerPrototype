import {
  Component,
  OnInit,
  Input,
  EventEmitter,
  ElementRef,
  ViewChild,
  Output
} from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { LoginService } from "app/main/content/_service/login.service";
import { User, UserRole } from "app/main/content/_model/user";
import { isNullOrUndefined } from "util";
import {
  ClassInstance,
  ClassArchetype,
  ClassInstanceDTO
} from "app/main/content/_model/meta/class";
import { Router } from "@angular/router";

@Component({
  selector: "inbox-overlay",
  templateUrl: "./inbox-overlay.component.html",
  styleUrls: ["./inbox-overlay.component.scss"]
})
export class InboxOverlayComponent implements OnInit {
  constructor(
    private router: Router,
    private marketplaceService: MarketplaceService,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private element: ElementRef
  ) {}

  isLoaded: boolean;
  @ViewChild("innerDiv", { static: false }) innerDiv: ElementRef;
  @ViewChild("actionDiv", { static: false }) actionDiv: ElementRef;

  @Output() closeOverlay: EventEmitter<boolean> = new EventEmitter<boolean>();

  marketplace: Marketplace;
  user;
  userRole: UserRole;
  classInstanceDTOs: ClassInstanceDTO[] = [];

  dataSource = new MatTableDataSource<ClassInstanceDTO>();
  displayedColumns = ["archetype", "label"];

  ngOnInit() {
    this.marketplaceService
      .findAll()
      .toPromise()
      .then((marketplaces: Marketplace[]) => {
        if (!isNullOrUndefined(marketplaces)) {
          this.marketplace = marketplaces[0];
        }

        this.loginService
          .getLoggedInUserRole()
          .toPromise()
          .then((role: UserRole) => {
            this.userRole = role;
            if (this.userRole === UserRole.VOLUNTEER) {
              this.loginService
                .getLoggedIn()
                .toPromise()
                .then((volunteer: User) => {
                  this.user = volunteer;
                });
            } else if (this.userRole === UserRole.HELP_SEEKER) {
              this.loginService
                .getLoggedIn()
                .toPromise()
                .then((user: User) => {
                  this.user = user;
                });
            }

            if (this.userRole === UserRole.VOLUNTEER) {
              this.user.subscribedTenants.forEach(tenantId => {
                this.classInstanceService
                  .getClassInstancesInUserInbox(
                    this.marketplace,
                    this.user.id,
                    this.user.subscribedTenants
                  )
                  .toPromise()
                  .then((ret: ClassInstanceDTO[]) => {
                    this.drawInboxElements(ret);
                    this.isLoaded = true;
                  });
              });
            } else if (this.userRole === UserRole.HELP_SEEKER) {
              this.classInstanceService
                .getClassInstancesInIssuerInbox(
                  this.marketplace,
                  this.user.tenantId
                )
                .toPromise()
                .then((ret: ClassInstanceDTO[]) => {
                  this.drawInboxElements(ret);
                  this.isLoaded = true;
                });
            }
          });
      });
  }

  drawInboxElements(classInstanceDTOs: ClassInstanceDTO[]) {
    if (!isNullOrUndefined(classInstanceDTOs)) {
      classInstanceDTOs.sort(
        (a, b) => a.blockchainDate.valueOf() - b.blockchainDate.valueOf()
      );

      if (classInstanceDTOs.length > 5) {
        classInstanceDTOs = classInstanceDTOs.slice(0, 5);
      }

      this.classInstanceDTOs = classInstanceDTOs;
      this.dataSource.data = classInstanceDTOs;
      this.innerDiv.nativeElement.style.width =
        this.element.nativeElement.parentElement.offsetWidth - 8 + "px";
      this.innerDiv.nativeElement.style.height =
        this.element.nativeElement.parentElement.offsetHeight - 58 + "px";
      this.innerDiv.nativeElement.style.overflow = "hidden";
      this.actionDiv.nativeElement.style.height = "18px";
    }
  }

  findNameProperty(entry: ClassInstance) {
    if (isNullOrUndefined(entry.properties)) {
      return "";
    }

    const name = entry.properties.find(p => p.id === "name");

    if (
      isNullOrUndefined(name) ||
      isNullOrUndefined(name.values) ||
      isNullOrUndefined(name.values[0])
    ) {
      return "";
    } else {
      return name.values[0];
    }
  }

  // getDateString(date: number) {
  //   return new Date(date).toLocaleDateString();
  // }

  getArchetypeIcon(entry: ClassInstance) {
    if (isNullOrUndefined(entry.imagePath)) {
      if (entry.classArchetype === ClassArchetype.COMPETENCE) {
        return "/assets/competence.jpg";
      } else if (entry.classArchetype === ClassArchetype.ACHIEVEMENT) {
        return "/assets/icons/achievements_black.png";
      } else if (entry.classArchetype === ClassArchetype.FUNCTION) {
        return "/assets/TODO";
      } else if (entry.classArchetype === ClassArchetype.TASK) {
        return "/assets/cog.png";
      } else {
        return "/assets/NONE";
      }
    } else {
      return entry.imagePath;
    }
  }

  showInboxClicked() {
    this.closeOverlay.emit(true);
    this.router.navigate(["/main/volunteer/asset-inbox"], {
      state: { marketplace: this.marketplace, participant: this.user }
    });
  }
}
