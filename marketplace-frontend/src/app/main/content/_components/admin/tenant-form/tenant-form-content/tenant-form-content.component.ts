import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { MatTableDataSource } from '@angular/material';
import { User, UserRole } from 'app/main/content/_model/user';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { FileInput } from 'ngx-material-file-input';

@Component({
  selector: "tenant-form-content",
  templateUrl: 'tenant-form-content.component.html',
  styleUrls: ['./tenant-form-content.component.scss'],
})
export class TenantFormContentComponent implements OnInit {

  tenantForm: FormGroup;
  @Input() tenant: Tenant;
  @Input() marketplace: Marketplace;

  dataSource = new MatTableDataSource<User>();
  displayedColumns = ['firstname', 'lastname', 'username', 'actions'];

  imageFileInput: FileInput;
  previewImage: any;
  uploadingImage: boolean;

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private tenantService: TenantService,
    private coreUserService: CoreUserService,
  ) {
    this.tenantForm = formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      primaryColor: new FormControl('', Validators.required),
      secondaryColor: new FormControl('', Validators.required),
    });
  }

  isEditMode() {
    return this.tenantForm.value.id !== null;
  }

  ngOnInit() {
    this.initializeTenantForm(this.tenant);
  }

  private async initializeTenantForm(tenant: Tenant) {
    if (isNullOrUndefined(tenant)) {
      return;
    }

    for (const key of Object.keys(tenant)) {
      this.tenantForm.controls[key].setValue(tenant[key]);
    }

    this.dataSource.data = <User[]>(
      await this.coreUserService
        .findAllByRoleAndTenantId(tenant.id, UserRole.HELP_SEEKER)
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


  upload() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async (e) => {
      console.log(e);
      const image = fileReader.result;
      this.uploadingImage = false;
      this.previewImage = image;


    };
    fileReader.readAsDataURL(this.imageFileInput.files[0]);
  }

  navigateToHelpSeekerForm(userId: string) {
    // TODO
  }
}
