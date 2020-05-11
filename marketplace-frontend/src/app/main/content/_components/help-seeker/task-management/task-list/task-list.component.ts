import { Component, OnInit, AfterViewInit, ViewChild } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { MatTableDataSource, MatPaginator, MatSort } from "@angular/material";
import { Participant } from "app/main/content/_model/participant";
import { Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Task } from "app/main/content/_model/task";
import { isNullOrUndefined } from "util";
import { Tenant } from "app/main/content/_model/tenant";
import { Helpseeker } from "app/main/content/_model/helpseeker";

@Component({
  selector: "fuse-task-list",
  templateUrl: "./task-list.component.html",
  styleUrls: ["./task-list.component.scss"],
  animations: fuseAnimations,
})
export class FuseTaskListComponent implements OnInit, AfterViewInit {
  marketplace: Marketplace;

  private classInstanceDTOs: ClassInstanceDTO[] = [];
  private tableDataSource = new MatTableDataSource<ClassInstanceDTO>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  private displayedColumns: string[] = [
    "taskName",
    "taskType1",
    "taskType2",
    "taskDateFrom",
    "taskDuration",
    "verified",
  ];

  private participant: Helpseeker;

  private tenant: Tenant;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private classInstanceService: ClassInstanceService,
    private tenantService: TenantService
  ) {}

  ngOnInit() {}

  async ngAfterViewInit() {
    this.participant = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );
    this.marketplace = <Marketplace>(
      await this.helpSeekerService
        .findRegisteredMarketplaces(this.participant.id)
        .toPromise()
    );
    this.tenant = <Tenant>(
      await this.tenantService.findById(this.participant.tenantId).toPromise()
    );

    this.tenantService.initHeader(this.tenant);

    this.tableDataSource.data = <ClassInstanceDTO[]>(
      await this.classInstanceService
        .getAllClassInstances(this.marketplace, this.tenant.id)
        .toPromise()
    );
    this.paginator.length = this.tableDataSource.data.length;
    this.tableDataSource.paginator = this.paginator;

    console.error(this.tableDataSource.data);
  }

  onRowSelect(task: Task) {
    // TODO @ALEX if edit of classInstances works ...
    // this.router.navigate(['/main/task/' + task.marketplaceId + '/' + task.id]);
  }

  addTask() {
    this.router.navigate(["/main/task-select"]);
  }
}
