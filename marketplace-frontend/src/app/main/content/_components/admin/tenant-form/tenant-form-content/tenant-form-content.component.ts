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
  landingPageImage: any;

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
    this.showProfileImageForm = false;
    this.showLandingPageImageForm = false;
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

    this.tenantForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      homepage: new FormControl(''),
      primaryColor: new FormControl('', Validators.required),
      secondaryColor: new FormControl('', Validators.required),
      landingpageMessage: new FormControl(''),
      landingpageText: new FormControl('')
    });

    for (const key of Object.keys(tenant)) {
      if (!isNullOrUndefined(this.tenantForm.controls[key])) {
        this.tenantForm.controls[key].setValue(tenant[key]);
      }
    }

    this.previewProfileImage = this.tenantService.getTenantProfileImage(this.tenant);
    this.addedTags = this.tenant.tags;
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }
    this.tenantForm.disable();


    const tenantId = this.tenant.id;
    this.tenant = new Tenant(this.tenantForm.value);
    this.tenant.id = tenantId;
    this.tenant.marketplaceId = this.globalInfo.marketplace.id;

    if (!isNullOrUndefined(this.previewProfileImage) && isString(this.previewProfileImage)) {

      const splitResult = (this.previewProfileImage as string).split(',');

      if (splitResult.length !== 2) {
        return;
      }

      const imageWrapper = new ImageWrapper({ imageInfo: splitResult[0], data: splitResult[1] });
      this.tenant.profileImage = imageWrapper;


    } else {
      this.tenant.profileImage = null;
    }
    this.tenant.tags = this.addedTags;



    this.tenantService
      .save(this.tenant).toPromise().then((tenant: Tenant) => {


        console.log("saved tenant");
        console.log(this.tenant);

        this.loginService.generateGlobalInfo(
          this.globalInfo.userRole,
          this.globalInfo.tenants.map((t) => t.id)
        ).then(() => {
          this.tenantForm.enable();
          this.roleChangeService.update();
          this.tenantSaved.emit(tenant);
          this.tenant = tenant;
          this.ngOnInit();
        });
      });
  }



  handleProfileImageUploadEvent(event: { key: string, image: any }) {
    console.log("image upload");
    console.log(event);

    this.previewProfileImage = event.image;
  }

  handleLandingImageUploadEvent(event: { key: string, image: any }) {
    console.log("image upload");
    console.log(event);

    this.landingPageImage = event.image;

  }

}
