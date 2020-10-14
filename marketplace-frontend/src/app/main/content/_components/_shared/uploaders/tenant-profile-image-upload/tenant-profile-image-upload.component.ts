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
import { FileService } from "app/main/content/_service/file.service";

@Component({
  selector: "tenant-profile-image-upload",
  templateUrl: "tenant-profile-image-upload.component.html",
  styleUrls: ["./tenant-profile-image-upload.component.scss"]
})
export class TenantProfileImageUploadComponent implements OnInit {
  @Input() tenant: Tenant;
  @Output() uploadedImage: EventEmitter<{
    key: string;
    url: string;
  }> = new EventEmitter();

  imageFileInput: FileInput;
  previewImage: string;
  uploadingImage: boolean;

  oldImage: string;

  loaded: boolean;

  constructor(
    private tenantService: TenantService,
    private fileService: FileService,
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
    this.previewImage = this.tenant.imagePath;
    this.oldImage = this.previewImage;
  }

  uploadImage() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async e => {
      const image = fileReader.result;
      this.uploadingImage = false;
      let payload: any = await this.fileService
        .uploadFile(this.imageFileInput.files[0])
        .toPromise();
      this.previewImage = payload.message;
      this.uploadedImage.emit({
        key: "uploaded",
        url: payload.message
      });
    };
    fileReader.readAsBinaryString(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
    this.uploadedImage.emit({ key: "clear", url: "" });
  }

  revertImage() {
    this.imageFileInput = undefined;
    this.uploadingImage = false;
    this.previewImage = this.oldImage;
    this.uploadedImage.emit({
      key: "reverted",
      url: this.oldImage
    });
  }
}
