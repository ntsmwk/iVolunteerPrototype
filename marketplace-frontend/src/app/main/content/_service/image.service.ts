import { Injectable } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";

@Injectable({ providedIn: "root" })
export class ImageService {
  constructor(private sanitizer: DomSanitizer) {}

  getImgSourceFromBytes(bytes: string) {
    let objectURL = "data:image/png;base64," + bytes;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }
}
