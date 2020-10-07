import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators
} from "@angular/forms";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { isNullOrUndefined } from "util";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginService } from "app/main/content/_service/login.service";
import { isString } from "highcharts";
import { RoleChangeService } from "app/main/content/_service/role-change.service";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { FileService } from "app/main/content/_service/file.service";

@Component({
  selector: "tenant-form-content",
  templateUrl: "tenant-form-content.component.html",
  styleUrls: ["./tenant-form-content.component.scss"]
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
    private fileService: FileService,
    private roleChangeService: RoleChangeService
  ) {}

  // TODO MWE/AK fix files....

  async ngOnInit() {
    this.loaded = false;
    this.previewProfileImageDirty = false;
    this.landingPageImageDirty = false;
    this.showProfileImageForm = false;
    this.showLandingPageImageForm = false;
    this.addedTags = [];
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
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

    tenant.landingpageMessage = this.patchLandingPageMessage(
      tenant.landingpageMessage
    );

    this.tenantForm = this.formBuilder.group({
      name: new FormControl("", Validators.required),
      description: new FormControl("", Validators.required),
      homepage: new FormControl(""),
      primaryColor: new FormControl("", Validators.required),
      secondaryColor: new FormControl("", Validators.required),
      landingpageMessage: new FormControl(""),
      landingpageTitle: new FormControl(""),
      landingpageText: new FormControl("")
    });

    for (const key of Object.keys(tenant)) {
      if (!isNullOrUndefined(this.tenantForm.controls[key])) {
        this.tenantForm.controls[key].setValue(tenant[key]);
      }
    }

    console.log(this.tenantForm.value);

    this.previewProfileImage = this.tenantService.getImagePath(this.tenant);
    this.addedTags = this.tenant.tags;
  }

  private patchLandingPageMessage(message: string) {
    if (isNullOrUndefined(message)) {
      message = "Herzlich Willkommen bei iVolunteer";
    }
    return message;
  }

  async save() {
    if (!this.tenantForm.valid) {
      return;
    }
    this.tenantForm.disable();

    const tenantId = this.tenant.id;

    // TODO fix if necessary....
    // const oldProfileImage = await this.imageService.findById(
    //   this.tenant.imageId
    // );
    // const oldLandingPageImage = await this.imageService.findById(
    //   this.tenant.landingpageImageId
    // );
    this.tenant = new Tenant(this.tenantForm.value);
    this.tenant.id = tenantId;
    this.tenant.marketplaceId = this.globalInfo.marketplace.id;
    this.tenant.landingpageMessage = this.patchLandingPageMessage(
      this.tenant.landingpageMessage
    );

    let profileImage = this.assignCurrentImage(
      this.previewProfileImage,
      this.previewProfileImageDirty,
      // oldProfileImage
      undefined
    );
    profileImage = await this.fileService.uploadFile(profileImage);
    this.tenant.imageFileName = "<<filenameWithoutPath.png>>";

    let landingPageImage = this.assignCurrentImage(
      this.landingPageImage,
      this.landingPageImageDirty,
      // oldLandingPageImage
      undefined
    );
    landingPageImage = await this.fileService.uploadFile(landingPageImage);
    this.tenant.landingpageImageFileName = "<<filenameWithoutPath.png>>";
    this.tenant.tags = this.addedTags;

    console.log(this.tenant);
    if (isNullOrUndefined(this.tenant.id)) {
      this.tenantService
        .createTenant(this.tenant)
        .toPromise()
        .then((response: { id: string }) => {
          this.tenant.id = response.id;
          this.loginService
            .generateGlobalInfo(
              this.globalInfo.userRole,
              this.globalInfo.tenants.map(t => t.id)
            )
            .then(() => {
              this.tenantForm.enable();
              this.roleChangeService.update();
              this.tenantSaved.emit(this.tenant);
              this.ngOnInit();
            });
        })
        .catch((error: HttpErrorResponse) => {
          console.error(error);
        });
    } else {
      this.tenantService
        .updateTenant(this.tenant)
        .toPromise()
        .then(() => {
          this.loginService
            .generateGlobalInfo(
              this.globalInfo.userRole,
              this.globalInfo.tenants.map(t => t.id)
            )
            .then(() => {
              this.tenantForm.enable();
              this.roleChangeService.update();
              this.tenantSaved.emit(this.tenant);
              this.ngOnInit();
            });
        })
        .catch((error: HttpErrorResponse) => {
          console.error(error);
        });
    }
  }

  assignCurrentImage(newImage: any, imageDirty: boolean, originalImage: any) {
    if (!imageDirty) {
      return originalImage;
    }

    if (!isNullOrUndefined(newImage) && isString(newImage)) {
      const splitResult = (newImage as string).split(",");

      if (splitResult.length !== 2) {
        return null;
      }

      // TODO MWE/AK fix
      // const imageWrapper = new ImageWrapper({
      //   imageInfo: splitResult[0],
      //   data: splitResult[1]
      // });
      // return imageWrapper;
    }
    return null;
  }

  handleProfileImageUploadEvent(event: { key: string; image: any }) {
    this.previewProfileImageDirty = true;
    this.previewProfileImage = event.image;
  }

  handleLandingPageImageUploadEvent(event: { key: string; image: any }) {
    this.landingPageImageDirty = true;
    this.landingPageImage = event.image;
  }
}
