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

  count = 0;

  async findById(imageId: string) {
    this.count++;
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
    if (this.count < 10) {
      console.error(this.count);
      console.error(this.imageMap);
      console.error(imageId);
      console.error(this.imageMap[imageId]);
    }
    if (this.imageMap[imageId]) {
      console.error("found: " + imageId);
      return new Promise((resolve, reject) => resolve(this.imageMap[imageId]));
    } else {
      if (this.count < 10) {
        let image: Image = <Image>(
          await this.http.get(`/core/image/${imageId}`).toPromise()
        );
        console.error(image);
        console.error("--------------------------");
        this.imageMap[imageId] = image;
      }
      return new Promise((resolve, reject) => resolve(this.imageMap[imageId]));
    }
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
