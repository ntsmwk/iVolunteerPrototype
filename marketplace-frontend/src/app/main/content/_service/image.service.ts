import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ImageWrapper } from '../_model/image';
import { isNullOrUndefined } from 'util';

@Injectable({ providedIn: 'root' })
export class ImageService {
  constructor(private sanitizer: DomSanitizer) { }

  getPNGSourceFromBytes(bytes: string) {
    const objectURL = 'data:image/png;base64,' + bytes;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  getImgSourceFromImageWrapper(imageWrapper: ImageWrapper) {

    if (isNullOrUndefined(imageWrapper)) {
      return null;
    }

    const objectURL = imageWrapper.imageInfo + ',' + imageWrapper.data;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }
}
