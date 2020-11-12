// import { Component, OnInit } from "@angular/core";
// import { Marketplace } from "app/main/content/_model/marketplace";
// import { MatTableDataSource } from "@angular/material";
// import { ClassArchetype } from "app/main/content/_model/meta/class";
// import { Tenant } from "app/main/content/_model/tenant";
// import { FormBuilder } from "@angular/forms";
// import { Router } from "@angular/router";
// import { LoginService } from "app/main/content/_service/login.service";
// import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
// import { isNullOrUndefined } from "util";
// import { ClassDefinition } from "app/main/content/_model/meta/class";
// import { User, UserRole } from "app/main/content/_model/user";
// import { GlobalInfo } from "app/main/content/_model/global-info";

// @Component({
//   templateUrl: "./task-select.component.html",
//   styleUrls: ["./task-select.component.scss"]
// })
// export class FuseTaskSelectComponent implements OnInit {
//   marketplace: Marketplace;
//   dataSource = new MatTableDataSource<ClassDefinition>();
//   displayedColumns = ["name", "configuration"];
//   user: User;
//   tenant: Tenant;
//   userRole: UserRole;

//   constructor(
//     formBuilder: FormBuilder,
//     private router: Router,
//     private loginService: LoginService,
//     private classDefinitionService: ClassDefinitionService
//   ) {}

//   async ngOnInit() {
//     let globalInfo = <GlobalInfo>(
//       await this.loginService.getGlobalInfo().toPromise()
//     );
//     this.user = globalInfo.user;
//     this.tenant = globalInfo.tenants[0];
//     this.marketplace = globalInfo.marketplace;
//     this.userRole = globalInfo.userRole;

//     if (!isNullOrUndefined(this.marketplace)) {
//       let tasks = <ClassDefinition[]>(
//         await this.classDefinitionService
//           .getByArchetype(this.marketplace, ClassArchetype.TASK, this.tenant.id)
//           .toPromise()
//       );

//       this.dataSource.data = tasks
//         .filter(t => t.configurationId != null)
//         .sort((c1, c2) => c1.configurationId.localeCompare(c2.configurationId));
//     }
//   }

//   onRowSelect(row) {
//     this.router.navigate([`main/instance-editor/${this.marketplace.id}`], {
//       queryParams: [row.id]
//     });
//   }
// }
