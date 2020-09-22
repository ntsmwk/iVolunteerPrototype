import { Component, OnInit, AfterViewInit, ViewChild } from "@angular/core";
import { fuseAnimations } from "@fuse/animations";
import { Marketplace } from "app/main/content/_model/marketplace";
import { ClassInstanceDTO } from "app/main/content/_model/meta/class";
import { MatTableDataSource, MatPaginator, MatSort } from "@angular/material";
import { User } from "app/main/content/_model/user";
import { Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { Tenant } from "app/main/content/_model/tenant";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "fuse-task-list",
  templateUrl: "./task-list.component.html",
  styleUrls: ["./task-list.component.scss"],
  animations: fuseAnimations
})
export class FuseTaskListComponent implements OnInit, AfterViewInit {
  marketplace: Marketplace;

  classInstanceDTOs: ClassInstanceDTO[] = [];
  tableDataSource = new MatTableDataSource<ClassInstanceDTO>();

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  private displayedColumns: string[] = [
    "taskName",
    "taskType1",
    "taskType2",
    "taskDateFrom",
    "taskDuration",
    "verified"
  ];

  user: User;
  tenant: Tenant;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService
  ) {}

  ngOnInit() {}

  async ngAfterViewInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.user = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.tenant = globalInfo.tenants[0];

    this.tableDataSource.paginator = this.paginator;
    this.tableDataSource.data = <ClassInstanceDTO[]>(
      await this.classInstanceService
        .getAllClassInstances(this.marketplace, this.tenant.id)
        .toPromise()
    );
  }

  rowSelected(task) {
    this.router.navigate(["main/details/" + task.id]);
  }

  addTask() {
    this.router.navigate(["/main/task-select"]);
  }
}
