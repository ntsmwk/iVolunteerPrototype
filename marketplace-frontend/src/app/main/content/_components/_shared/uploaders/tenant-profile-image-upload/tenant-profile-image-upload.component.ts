import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SecurityContext
} from "@angular/core";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";
import { isNullOrUndefined } from "util";
import { FileInput } from "ngx-material-file-input";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: "tenant-profile-image-upload",
  templateUrl: "tenant-profile-image-upload.component.html",
  styleUrls: ["./tenant-profile-image-upload.component.scss"]
})
export class TenantProfileImageUploadComponent implements OnInit {
  @Input() tenant: Tenant;
  @Output() uploadedImage: EventEmitter<{
    key: string;
    image: any;
    fileName: string;
  }> = new EventEmitter();

  imageFileInput: FileInput;
  previewImage: any;
  uploadingImage: boolean;

  oldImage: any;

  loaded: boolean;

  // TODO MWE/AK fix for new file upload

  constructor(
    private tenantService: TenantService,
    private sanitizer: DomSanitizer
  ) {}

  async ngOnInit() {
    this.loaded = false;
    this.uploadingImage = false;
    this.imageFileInput = undefined;
    this.oldImage = undefined;
    this.previewImage = undefined;

    await this.initialize(this.tenant);
    this.loaded = true;
  }

  private async initialize(tenant: Tenant) {
    if (isNullOrUndefined(tenant)) {
      return;
    }
    this.previewImage = this.tenantService.getImagePath(this.tenant);
    this.oldImage = this.previewImage;
  }

  uploadImage() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async e => {
      const image = fileReader.result;
      this.uploadingImage = false;
      this.previewImage = image;
      console.error(image);
      console.error(this.imageFileInput.files[0]);
      this.uploadedImage.emit({
        key: "uploaded",
        image: image,
        fileName: this.imageFileInput.files[0].name
      });
    };
    fileReader.readAsBinaryString(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
    this.uploadedImage.emit({ key: "clear", image: null, fileName: "" });
  }

  revertImage() {
    this.imageFileInput = undefined;
    this.uploadingImage = false;
    this.previewImage = this.oldImage;
    this.uploadedImage.emit({
      key: "reverted",
      image: this.oldImage,
      fileName: ""
    });
  }
}
