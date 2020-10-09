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
  @Output() uploadedImage: EventEmitter<{
    key: string;
    image: any;
  }> = new EventEmitter();

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

  // TODO MWE/AK fix ....

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
    const userImagePath = this.user.profileFileName;
    this.previewImage = this.userService.getUserProfileImage(this.user);

    this.oldImage = this.previewImage;
  }

  uploadImage() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async e => {
      const image = fileReader.result;
      this.uploadingImage = false;
      this.previewImage = image;
      this.uploadedImage.emit({ key: "uploaded", image });
    };
    fileReader.readAsDataURL(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
    this.uploadedImage.emit({ key: "clear", image: null });
  }

  revertImage() {
    this.imageFileInput = undefined;
    this.uploadingImage = false;
    this.previewImage = this.oldImage;
    this.uploadedImage.emit({ key: "reverted", image: this.oldImage });
  }
}
