import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { FileInput } from 'ngx-material-file-input';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { UserRole } from 'app/main/content/_model/user';
import { LoginService } from 'app/main/content/_service/login.service';
import { ImageService } from 'app/main/content/_service/image.service';
import { ImageWrapper } from 'app/main/content/_model/image';
import { isString } from 'highcharts';
import { RoleChangeService } from 'app/main/content/_service/role-change.service';

@Component({
  selector: "tenant-form-content",
  templateUrl: 'tenant-form-content.component.html',
  styleUrls: ['./tenant-form-content.component.scss'],
})
export class TenantFormContentComponent implements OnInit {

  tenantForm: FormGroup;
  addedTags: string[];
  @Input() tenant: Tenant;
  @Output() tenantSaved: EventEmitter<any> = new EventEmitter();
  globalInfo: GlobalInfo;

  previewProfileImage: any;
  previewProfileImageDirty: boolean;
  landingPageImage: any;
  landingPageImageDirty: boolean;


  loaded: boolean;
  showProfileImageForm: boolean;

  showLandingPageImageForm: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private tenantService: TenantService,
    private loginService: LoginService,
    private roleChangeService: RoleChangeService,

  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.previewProfileImageDirty = false;
    this.landingPageImageDirty = false;
    this.showProfileImageForm = false;
    this.showLandingPageImageForm = false;
    this.addedTags = [];
    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    await this.initializeTenantForm(this.tenant);
    this.loaded = true;

    setTimeout(() => {
      this.showProfileImageForm = true;
      this.showLandingPageImageForm = true;
    }, 100);
  }

  private async initializeTenantForm(tenant: Tenant) {
    if (isNullOrUndefined(tenant)) {
      return;
    }

    tenant.landingpageTitle = this.patchLandingPageTitle(tenant.landingpageTitle);

    this.tenantForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      homepage: new FormControl(''),
      primaryColor: new FormControl('', Validators.required),
      secondaryColor: new FormControl('', Validators.required),
      landingpageMessage: new FormControl(''),
      landingpageTitle: new FormControl(''),
      landingpageText: new FormControl(''),
    });



    for (const key of Object.keys(tenant)) {
      if (!isNullOrUndefined(this.tenantForm.controls[key])) {
        this.tenantForm.controls[key].setValue(tenant[key]);
      }
    }

    console.log(this.tenantForm.value);

    this.previewProfileImage = this.tenantService.getTenantProfileImage(this.tenant);
    this.addedTags = this.tenant.tags;
  }

  private patchLandingPageTitle(title: string) {
    if (isNullOrUndefined(title)) {
      title = 'Herzlich Willkommen bei iVolunteer';
    }
    return title;
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }
    this.tenantForm.disable();


    const tenantId = this.tenant.id;
    const oldProfileImage = this.tenant.profileImage;
    const oldLandingPageImage = this.tenant.landingpageImage;
    this.tenant = new Tenant(this.tenantForm.value);
    this.tenant.id = tenantId;
    this.tenant.marketplaceId = this.globalInfo.marketplace.id;
    this.tenant.landingpageTitle = this.patchLandingPageTitle(this.tenant.landingpageTitle);

    this.tenant.profileImage = this.assignCurrentImage(this.previewProfileImage, this.previewProfileImageDirty, oldProfileImage);
    this.tenant.landingpageImage = this.assignCurrentImage(this.landingPageImage, this.landingPageImageDirty, oldLandingPageImage);
    this.tenant.tags = this.addedTags;



    this.tenantService.save(this.tenant).toPromise().then((tenant: Tenant) => {
      this.loginService.generateGlobalInfo(this.globalInfo.userRole, this.globalInfo.tenants.map((t) => t.id))
        .then(() => {
          this.tenantForm.enable();
          this.roleChangeService.update();
          this.tenantSaved.emit(tenant);
          this.tenant = tenant;
          this.ngOnInit();
        });
    });
  }

  assignCurrentImage(newImage: any, imageDirty: boolean, originalImage: any) {
    if (!imageDirty) {
      return originalImage;
    }

    if (!isNullOrUndefined(newImage) && isString(newImage)) {
      const splitResult = (newImage as string).split(',');

      if (splitResult.length !== 2) { return null; }

      const imageWrapper = new ImageWrapper({ imageInfo: splitResult[0], data: splitResult[1] });
      return imageWrapper;

    }

    return null;

  }



  handleProfileImageUploadEvent(event: { key: string, image: any }) {
    console.log("image upload");
    console.log(event);
    this.previewProfileImageDirty = true;
    this.previewProfileImage = event.image;
  }

  handleLandingPageImageUploadEvent(event: { key: string, image: any }) {
    console.log("image upload");
    console.log(event);
    this.landingPageImageDirty = true;
    this.landingPageImage = event.image;

  }

}
