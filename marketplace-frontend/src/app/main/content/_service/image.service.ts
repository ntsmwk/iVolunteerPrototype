import { Injectable } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ImageWrapper, Image } from "../_model/image";
import { isNullOrUndefined } from "util";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })
export class ImageService {
  constructor(private sanitizer: DomSanitizer, private http: HttpClient) {}

  findById(imageId: string) {
    return this.http.get(`/core/image/${imageId}`);
  }

  createImage(image: Image) {
    return this.http.post(`/core/image/new`, image);
  }

  updateImage(image: Image) {
    return this.http.put(`/core/image/update`, image);
  }

  deleteImage(imageId: string) {
    return this.http.delete(`/core/image/${imageId}`);
  }

  getImgSourceFromImageWrapper(imageWrapper: ImageWrapper) {
    if (isNullOrUndefined(imageWrapper)) {
      return null;
    }

    const objectURL = imageWrapper.imageInfo + "," + imageWrapper.data;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }
}
