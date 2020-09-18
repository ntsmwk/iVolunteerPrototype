import { Component, OnInit, Input } from '@angular/core';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Tenant } from 'app/main/content/_model/tenant';
import { UserRole, User } from 'app/main/content/_model/user';
import { MatTableDataSource } from '@angular/material';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';

@Component({
  selector: "tenant-helpseekers-form",
  templateUrl: 'helpseekers-form.component.html',
  styleUrls: ['./helpseekers-form.component.scss'],
  providers: [DialogFactoryDirective]
})
export class TenantHelpseekersFormComponent implements OnInit {

  loaded: boolean;
  @Input() tenant: Tenant;

  dataSource = new MatTableDataSource<User>();
  displayedColumns = ['firstname', 'lastname', 'username', 'actions'];

  constructor(
    private coreUserService: CoreUserService,
    private dialogFactory: DialogFactoryDirective,
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.dataSource.data = [];

    this.dataSource.data = <User[]>(
      await this.coreUserService.findAllByRoleAndTenantId(this.tenant.id, UserRole.HELP_SEEKER).toPromise()
    );
    console.error(this.dataSource.data);
    this.loaded = true;
  }

  addHelpseeker() {
    this.dialogFactory.openAddHelpseekerDialog(this.dataSource.data).then(ret => {
      console.log(ret);
    });
  }


}
