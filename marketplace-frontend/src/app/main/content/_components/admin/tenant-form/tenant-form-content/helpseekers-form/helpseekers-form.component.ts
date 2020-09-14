import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TenantTagService } from 'app/main/content/_service/tenant-tag.service';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Tenant } from 'app/main/content/_model/tenant';
import { UserRole, User } from 'app/main/content/_model/user';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: "tenant-helpseekers-form",
  templateUrl: 'helpseekers-form.component.html',
  styleUrls: ['./helpseekers-form.component.scss']
})
export class TenantHelpseekersFormComponent implements OnInit {

  loaded: boolean;
  @Input() tenant: Tenant;

  dataSource = new MatTableDataSource<User>();
  displayedColumns = ['firstname', 'lastname', 'username', 'actions'];

  constructor(
    private coreUserService: CoreUserService,
  ) { }

  async ngOnInit() {
    this.loaded = false;

    this.dataSource.data = <User[]>(
      await this.coreUserService
        .findAllByRoleAndTenantId(this.tenant.id, UserRole.HELP_SEEKER)
        .toPromise()
    );
    console.error(this.dataSource.data);
    this.loaded = true;
  }


}
