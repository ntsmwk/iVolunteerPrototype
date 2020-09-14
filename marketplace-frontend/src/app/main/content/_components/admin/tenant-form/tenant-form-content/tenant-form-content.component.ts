import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
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
  addedTags: string[];
  @Input() tenant: Tenant;
  @Input() marketplace: Marketplace;

  imageFileInput: FileInput;
  previewImage: any;
  uploadingImage: boolean;

  loaded: boolean;
  showHelpseekersForm: boolean;

  constructor(
    private formBuilder: FormBuilder,
  ) { }

  isEditMode() {
    return this.tenantForm.value.id !== null;
  }

  ngOnInit() {
    this.loaded = false;
    this.showHelpseekersForm = false;
    this.initializeTenantForm(this.tenant);
    this.loaded = true;
  }

  private async initializeTenantForm(tenant: Tenant) {
    if (isNullOrUndefined(tenant)) {
      return;
    }

    this.tenantForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      homepage: new FormControl(''),
      primaryColor: new FormControl('', Validators.required),
      secondaryColor: new FormControl('', Validators.required),
    });

    for (const key of Object.keys(tenant)) {
      if (!isNullOrUndefined(this.tenantForm.controls[key])) {
        this.tenantForm.controls[key].setValue(tenant[key]);
      }
    }

    this.previewImage = this.tenant.image;
    this.addedTags = this.tenant.tags;
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }
    this.tenant = new Tenant(this.tenantForm.value);

    this.tenant.marketplaceId = this.marketplace.id;
    this.tenant.image = this.previewImage;

    this.tenant.tags = this.addedTags;
    this.showHelpseekersForm = true;

    console.log(this.tenant);

    // this.tenantService
    //   .save(<Tenant>this.tenantForm.value)
    //   .toPromise()
    //   .then(() =>
    //     this.router.navigate([`/main/marketplace-form/${this.marketplace.id}`])
    //   );
  }


  uploadImage() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async (e) => {
      const image = fileReader.result;
      this.uploadingImage = false;
      this.previewImage = image;
    };
    fileReader.readAsDataURL(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
  }

}
