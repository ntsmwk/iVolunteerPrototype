import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ImageService } from "./image.service";
import { UserRole } from "../_model/user";
import { UserImage } from "../_model/image";
import { isNullOrUndefined } from "util";

@Injectable({ providedIn: "root" })
export class CoreUserImageService {
  constructor(private http: HttpClient, private imageService: ImageService) {}

  findByRoleAndTenantId(tenantId: string, role: UserRole) {
    return this.http.get(`/core/image/role/${role}/tenant/${tenantId}`);
  }

  findByUser() {
    return this.http.get(`/core/image/user`);
  }

  createUserImage(userImage: UserImage) {
    return this.http.post(`/core/image/new`, userImage);
  }

  updateUserImage(userImage: UserImage) {
    return this.http.put(`/core/image/update`, userImage);
  }

  deleteUserImage(userId: string) {
    return this.http.delete(`/core/image/${userId}`);
  }

  getUserProfileImage(userImage: UserImage) {
    if (isNullOrUndefined(userImage)) {
      return "/assets/images/avatars/profile.jpg";
    }
    const ret = this.imageService.getImgSourceFromImageWrapper(
      userImage.imageWrapper
    );
    if (ret == null) {
      return "/assets/images/avatars/profile.jpg";
    } else {
      return ret;
    }
  }
}