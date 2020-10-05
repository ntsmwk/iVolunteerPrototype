import { Injectable } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ImageWrapper, Image } from "../_model/image";
import { isNullOrUndefined } from "util";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class ImageService {
  constructor(private sanitizer: DomSanitizer, private http: HttpClient) {}

  imageMap = {};

  async findById(imageId: string) {
    return new Promise((resolve, reject) => {
      this.retriveAndStoreImageById(imageId).then(img => {
        if (img) {
          resolve(img);
        } else {
          resolve(null);
        }
      });
    });
  }

  private async retriveAndStoreImageById(imageId: string) {
    if (this.imageMap[imageId]) {
      return new Promise((resolve, reject) => resolve(this.imageMap[imageId]));
    }
    let image: Image = <Image>(
      await this.http.get(`/core/image/${imageId}`).toPromise()
    );
    this.imageMap[imageId] = image;
    return new Promise((resolve, reject) => resolve(image));
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
