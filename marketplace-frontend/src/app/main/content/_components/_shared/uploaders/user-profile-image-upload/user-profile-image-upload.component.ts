import { Component, OnInit, Input, Output, EventEmitter, SecurityContext } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { FileInput } from 'ngx-material-file-input';
import { DomSanitizer } from '@angular/platform-browser';
import { User } from 'app/main/content/_model/user';
import { CoreUserImageService } from 'app/main/content/_service/core-user-image.service';
import { UserImage } from 'app/main/content/_model/image';

@Component({
  selector: "user-profile-image-upload",
  templateUrl: 'user-profile-image-upload.component.html',
  styleUrls: ['./user-profile-image-upload.component.scss'],
})
export class UserProfileImageUploadComponent implements OnInit {

  @Input() user: User;
  @Output() uploadedImage: EventEmitter<{ key: string, image: any }> = new EventEmitter();

  imageFileInput: FileInput;
  previewImage: any;
  uploadingImage: boolean;

  oldImage: any;

  loaded: boolean;

  constructor(
    private userImageService: CoreUserImageService,
    private sanitizer: DomSanitizer
  ) { }

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
    const userImage = <UserImage>await this.userImageService.findByUserId(this.user.id).toPromise();
    this.previewImage = this.userImageService.getUserProfileImage(userImage);

    this.oldImage = this.sanitizer.sanitize(SecurityContext.URL, this.previewImage);
  }


  uploadImage() {
    this.uploadingImage = true;
    const fileReader = new FileReader();
    fileReader.onload = async (e) => {
      const image = fileReader.result;
      this.uploadingImage = false;
      this.previewImage = image;
      this.uploadedImage.emit({ key: 'uploaded', image });
    };
    fileReader.readAsDataURL(this.imageFileInput.files[0]);
  }

  deleteImage() {
    this.imageFileInput = undefined;
    this.previewImage = undefined;
    this.uploadedImage.emit({ key: 'clear', image: null });
  }

  revertImage() {
    this.imageFileInput = undefined;
    this.uploadingImage = false;
    this.previewImage = this.oldImage;
    this.uploadedImage.emit({ key: 'reverted', image: this.oldImage });
  }

}
