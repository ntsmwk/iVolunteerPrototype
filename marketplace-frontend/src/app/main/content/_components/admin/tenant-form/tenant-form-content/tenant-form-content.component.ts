import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { MatTableDataSource } from '@angular/material';
import { User, UserRole } from 'app/main/content/_model/user';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Marketplace } from 'app/main/content/_model/marketplace';

@Component({
  selector: "tenant-form-content",
  templateUrl: 'tenant-form-content.component.html',
})
export class TenantFormContentComponent implements OnInit {

  tenantForm: FormGroup;
  @Input() tenant: Tenant;
  @Input() marketplace: Marketplace;

  dataSource = new MatTableDataSource<User>();
  displayedColumns = ['firstname', 'lastname', 'username', 'actions'];

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private tenantService: TenantService,
    private coreUserService: CoreUserService,
  ) {
    this.tenantForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined, Validators.required),
      primaryColor: new FormControl(undefined, Validators.required),
      secondaryColor: new FormControl(undefined, Validators.required),
    });
  }

  isEditMode() {
    return this.tenantForm.value.id !== null;
  }

  ngOnInit() {
    this.initializeTenantForm(this.tenant.id);
  }

  private async initializeTenantForm(tenantId: string) {
    if (tenantId == null || tenantId.length === 0) {
      return;
    }
    this.tenant = <Tenant>(
      await this.tenantService.findById(tenantId).toPromise()
    );
    this.tenantForm.setValue({
      id: this.tenant.id,
      name: this.tenant.name,
      primaryColor: this.tenant.primaryColor,
      secondaryColor: this.tenant.secondaryColor,
    });

    this.dataSource.data = <User[]>(
      await this.coreUserService
        .findAllByRoleAndTenantId(this.tenant.id, UserRole.HELP_SEEKER)
        .toPromise()
    );
    console.error(this.dataSource.data);
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }
    this.tenant = <Tenant>this.tenantForm.value;
    this.tenant.marketplaceId = this.marketplace.id;

    this.tenantService
      .save(<Tenant>this.tenantForm.value)
      .toPromise()
      .then(() =>
        this.router.navigate([`/main/marketplace-form/${this.marketplace.id}`])
      );
  }

  navigateToHelpSeekerForm(userId: string) {
    // TODO
  }
}
