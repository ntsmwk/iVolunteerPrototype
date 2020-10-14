import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SecurityContext
} from "@angular/core";
import { isNullOrUndefined } from "util";
import { FileInput } from "ngx-material-file-input";
import { DomSanitizer } from "@angular/platform-browser";
import { User } from "app/main/content/_model/user";
import { FileService } from "app/main/content/_service/file.service";
import { CoreUserService } from "app/main/content/_service/core-user.service";

@Component({
  selector: "user-profile-image-upload",
  templateUrl: "user-profile-image-upload.component.html",
  styleUrls: ["./user-profile-image-upload.component.scss"]
})
export class UserProfileImageUploadComponent implements OnInit {
  @Input() user: User;
  @Output() onSave = new EventEmitter();

  imageFileInput: FileInput;
  previewImage: any;
  uploadingImage: boolean;

  oldImage: any;

  loaded: boolean;

  constructor(
    private sanitizer: DomSanitizer,
    private userService: CoreUserService,
    private fileService: FileService
  ) {}

  async ngOnInit() {
    this.loaded = false;
    this.uploadingImage = false;
    this.imageFileInput = undefined;
    this.oldImage = undefined;
    this.previewImage = undefined;

    await this.initialize(this.user);
    this.loaded = true;
  }

  private async initialize(user: User) {
    if (isNullOrUndefined(user)) {
      return;
    }
    this.previewImage = this.user.profileImagePath;

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
    };
    fileReader.readAsDataURL(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
  }

  revertImage() {
    this.imageFileInput = undefined;
    this.uploadingImage = false;
    this.previewImage = this.oldImage;
  }

  async saveProfileImg() {
    this.user.profileImagePath = this.previewImage;
    await this.userService.updateUser(this.user, true).toPromise();
    this.onSave.emit();
  }
}
