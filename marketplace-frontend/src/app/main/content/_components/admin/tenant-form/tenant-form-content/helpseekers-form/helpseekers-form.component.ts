import { Component, OnInit, Input } from '@angular/core';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Tenant } from 'app/main/content/_model/tenant';
import { UserRole, User } from 'app/main/content/_model/user';
import { MatTableDataSource } from '@angular/material';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { isNullOrUndefined } from 'util';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';

@Component({
  selector: "tenant-helpseekers-form",
  templateUrl: 'helpseekers-form.component.html',
  styleUrls: ['./helpseekers-form.component.scss'],
  providers: [DialogFactoryDirective]
})
export class TenantHelpseekersFormComponent implements OnInit {

  loaded: boolean;
  @Input() tenant: Tenant;
  helpseekers: User[];
  globalInfo: GlobalInfo;

  dataSource = new MatTableDataSource<User>();
  displayedColumns = ['firstname', 'lastname', 'username', 'actions'];

  constructor(
    private coreUserService: CoreUserService,
    private dialogFactory: DialogFactoryDirective,
    private loginService: LoginService,
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.dataSource.data = [];

    await Promise.all([
      this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise(),
      this.helpseekers = <User[]>(
        await this.coreUserService.findAllByRoleAndTenantId(this.tenant.id, UserRole.HELP_SEEKER).toPromise()
      )
    ]);

    this.dataSource.data = this.helpseekers;
    this.loaded = true;
  }

  addHelpseeker() {
    this.dialogFactory.openAddHelpseekerDialog(this.helpseekers).then(ret => {
      if (!isNullOrUndefined(ret)) {
        this.helpseekers = ret.helpseekers;
        this.dataSource.data = this.helpseekers;
      }
    });
  }

  removeHelpseeker(helpseeker: User) {
    console.log(helpseeker);
    this.coreUserService.unsubscribeUserFromTenant(helpseeker.id, this.globalInfo.marketplace.id, this.tenant.id, UserRole.HELP_SEEKER).toPromise().then((ret: User) => {
      console.log(ret);
      if (!isNullOrUndefined(ret)) {
        this.helpseekers = this.helpseekers.filter(h => h.id !== ret.id);
        this.dataSource.data = this.helpseekers;
      }
    });
  }


}
